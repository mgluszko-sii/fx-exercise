package com.example.fxexercise.message.converter;

public interface MessageConverter<S,T> {

    T convert(S message);
}
