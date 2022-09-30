package com.example.fxexercise.controller;


import com.example.fxexercise.repository.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.example.fxexercise.services.PriceService;

import java.util.Optional;

@RestController
public class PricePublisherController {

    @Autowired
    private PriceService messageService;

    @GetMapping("/prices/{instrumentName}")
    public PriceDTO getPriceByInstrumentName(@PathVariable String instrumentName){
        Optional<Price> priceOptional = messageService.getPriceForInstrument(instrumentName);
        Price price = priceOptional.orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, String.format("Price for instrument %S not found", instrumentName)));

        return PriceDTO.fromPrice(price);
    }

}
