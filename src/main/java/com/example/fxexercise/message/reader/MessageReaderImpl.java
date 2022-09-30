package com.example.fxexercise.message.reader;

import com.example.fxexercise.message.converter.MessageConverter;
import com.example.fxexercise.repository.Price;
import com.example.fxexercise.services.PriceService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.StringReader;

@Component
@AllArgsConstructor
public class MessageReaderImpl implements MessageReader<String, Price> {

    @Autowired
    private PriceService priceService;

    @Override
    public void process(String message, MessageConverter<String, Price> converter) {
        BufferedReader reader = new BufferedReader(new StringReader(message));

        reader.lines()
                .map(converter::convert)
                .forEach(priceService::addPrice);
    }

}
