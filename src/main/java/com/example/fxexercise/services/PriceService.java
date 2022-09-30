package com.example.fxexercise.services;

import com.example.fxexercise.repository.Price;

import java.util.Optional;

public interface PriceService {

    Optional<Price> getPriceForInstrument(String instrument);

    void addPrice(Price price);
}
