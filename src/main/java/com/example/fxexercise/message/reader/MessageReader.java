package com.example.fxexercise.message.reader;

import com.example.fxexercise.message.converter.MessageConverter;

public interface MessageReader<S,T> {

    void process(S message, MessageConverter<S,T> converter);

}
