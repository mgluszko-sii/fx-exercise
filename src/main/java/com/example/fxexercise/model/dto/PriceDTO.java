package com.example.fxexercise.model.dto;

import com.example.fxexercise.model.Price;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PriceDTO {

    private final String bid;
    private final String ask;

    public static PriceDTO fromPrice(Price price){
        return PriceDTO.builder()
                .ask(price.getAsk().toPlainString())
                .bid(price.getBid().toPlainString())
                .build();
    }
}
