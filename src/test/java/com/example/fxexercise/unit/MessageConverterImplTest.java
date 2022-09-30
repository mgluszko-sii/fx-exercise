package com.example.fxexercise.unit;

import com.example.fxexercise.message.converter.ConversionException;
import com.example.fxexercise.message.converter.MessageConverterImpl;
import com.example.fxexercise.repository.Price;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest
public class MessageConverterImplTest {

    @Autowired
    private MessageConverterImpl converter;

    @Test
    public void convertCorrectData(){
        //given
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss:SSS");
        String message = "106,EUR/USD,1.1000,1.2000,01-06-2020 12:01:01:001";
        Price expectedPrice = Price.builder()
                .id("106")
                .instrument("EUR/USD")
                .bid(new BigDecimal("1.1000"))
                .ask(new BigDecimal("1.2000"))
                .timestamp(LocalDateTime.parse("01-06-2020 12:01:01:001", formatter))
                .build();
        //when
        Price price = converter.convert(message);
        //then
        Assertions.assertEquals(expectedPrice, price);
    }

    @Test
    public void handleIncorrectData(){
        String message = "incorrect message, ok?";
        Assertions.assertThrows(ConversionException.class, () -> converter.convert(message));
    }
}
