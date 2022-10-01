package com.example.fxexercise.integration;

import com.example.fxexercise.controller.PriceDTO;
import com.example.fxexercise.message.converter.MessageConverter;
import com.example.fxexercise.message.reader.MessageReader;
import com.example.fxexercise.repository.MessageRepository;
import com.example.fxexercise.repository.Price;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FxExerciseApplicationTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private MessageReader<String, Price> reader;

	@Autowired
	private MessageConverter<String, Price> converter;

	@Autowired
	private MessageRepository<Price> repository;

	private final String RESOURCE_DIR_PATH = "src/test/resources/";

	@AfterEach
	void clearCache(){
		repository.clear();
	}

	@Test
	public void testWithPathVariableEndpoint() throws IOException{
		//given
		String testData = readFile("test_feed_1.csv");
		String results = readFile("expected_results_1.json");
		List<Result> expectedResults = mapResultsFromJson((results));

		Map<String,Result> mappedExpectedResults = expectedResults.stream()
				.collect(Collectors.toMap(result -> result.getInstrument() , result -> result));

		//when
		reader.process(testData, converter);
		Map<Result,PriceDTO> mappedResults = expectedResults.stream()
				.collect(Collectors.toMap(
						result -> result,
						result -> getResponseFromEndpoint(result.getInstrument())));

		//then
		mappedExpectedResults.keySet().stream().forEach(
				instrument ->	{
						Result expected = mappedExpectedResults.get(instrument);
						PriceDTO actual = mappedResults.get(expected);
						Assertions.assertEquals(expected.getBid(), actual.getBid());
						Assertions.assertEquals(expected.getAsk(), actual.getAsk());
				}
		);
	}

	@Test
	public void testWithRequestParamEndpoint() throws IOException{
		//given
		String testData = readFile("test_feed_2.csv");
		String results = readFile("expected_results_2.json");
		List<Result> expectedResults = mapResultsFromJson((results));

		Map<String,Result> mappedExpectedResults = expectedResults.stream()
				.collect(Collectors.toMap(result -> result.getInstrument() , result -> result));

		//when
		reader.process(testData, converter);
		Map<Result,PriceDTO> mappedResults = expectedResults.stream()
				.collect(Collectors.toMap(
						result -> result,
						result -> getResponseFromEndpoint("?instrumentName="+ result.getInstrument())));

		//then
		mappedExpectedResults.keySet().stream().forEach(
				instrument ->	{
					Result expected = mappedExpectedResults.get(instrument);
					PriceDTO actual = mappedResults.get(expected);
					Assertions.assertEquals(expected.getBid(), actual.getBid());
					Assertions.assertEquals(expected.getAsk(), actual.getAsk());
				}
		);
	}

	@Test
	public void testWithMultipleMessages() throws IOException{
		//given
		String testData_1 = readFile("test_feed_1.csv");
		String testData_2 = readFile("test_feed_2.csv");
		String results = readFile("expected_results_2.json");
		List<Result> expectedResults = mapResultsFromJson((results));

		Map<String,Result> mappedExpectedResults = expectedResults.stream()
				.collect(Collectors.toMap(result -> result.getInstrument() , result -> result));

		//when
		reader.process(testData_1, converter);
		reader.process(testData_2, converter);
		Map<Result,PriceDTO> mappedResults = expectedResults.stream()
				.collect(Collectors.toMap(
						result -> result,
						result -> getResponseFromEndpoint(result.getInstrument())));

		//then
		mappedExpectedResults.keySet().stream().forEach(
				instrument ->	{
					Result expected = mappedExpectedResults.get(instrument);
					PriceDTO actual = mappedResults.get(expected);
					Assertions.assertEquals(expected.getBid(), actual.getBid());
					Assertions.assertEquals(expected.getAsk(), actual.getAsk());
				}
		);
	}

	private PriceDTO getResponseFromEndpoint(String instrument){
		return restTemplate.getForObject("http://localhost:" + port + "/prices/" + instrument,
				PriceDTO.class);
	}

	private List<Result> mapResultsFromJson(String text) throws IOException {
		ObjectMapper mapper = new ObjectMapper();

		return mapper.readValue(text, new TypeReference<List<Result>>(){});
	}

	private String readFile(String path) throws IOException {
		FileInputStream fis = new FileInputStream(RESOURCE_DIR_PATH + path);
		return IOUtils.toString(fis, StandardCharsets.UTF_8);
	}

	@Data
	static class Result {
		private String instrument;
		private BigDecimal bid;
		private BigDecimal ask;
	}

}
