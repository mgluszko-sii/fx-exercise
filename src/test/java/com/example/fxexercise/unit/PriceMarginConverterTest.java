package com.example.fxexercise.unit;

import com.example.fxexercise.repository.Price;
import com.example.fxexercise.services.PriceMarginConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest
public class PriceMarginConverterTest {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss:SSS");

    @Autowired
    private PriceMarginConverter converter;


    @Test
    public void testPriceConversion(){
        //given
        Price initialPrice = Price.builder()
                .id("106")
                .instrument("EUR/USD")
                .bid(new BigDecimal("1.1000"))
                .ask(new BigDecimal("1.2000"))
                .timestamp(LocalDateTime.parse("01-06-2020 12:01:01:001", formatter))
                .build();
        Price expectedPrice = Price.builder()
                .id("106")
                .instrument("EUR/USD")
                .bid(new BigDecimal("1.0989"))
                .ask(new BigDecimal("1.2012"))
                .timestamp(LocalDateTime.parse("01-06-2020 12:01:01:001", formatter))
                .build();
        //when
        Price convertedPrice = converter.convert(initialPrice);
        //then
        Assertions.assertEquals(expectedPrice, convertedPrice);
    }

}
