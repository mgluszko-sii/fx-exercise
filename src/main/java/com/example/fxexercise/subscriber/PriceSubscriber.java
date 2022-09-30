package com.example.fxexercise.subscriber;

import com.example.fxexercise.message.converter.MessageConverter;
import com.example.fxexercise.message.reader.MessageReader;
import com.example.fxexercise.repository.Price;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Setter
@NoArgsConstructor
public class PriceSubscriber implements MessageSubscriber {

    private MessageReader<String, Price> reader;
    private MessageConverter<String, Price> converter;

    @Override
    public void onMessage(String message) {
        reader.process(message, converter);
    }
}
