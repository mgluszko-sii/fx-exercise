package com.example.fxexercise.controller;

import com.example.fxexercise.repository.Price;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
public class PriceDTO {

    private final BigDecimal bid;
    private final BigDecimal ask;

    public static PriceDTO fromPrice(Price price){
        return PriceDTO.builder()
                .ask(price.getAsk())
                .bid(price.getBid())
                .build();
    }
}
