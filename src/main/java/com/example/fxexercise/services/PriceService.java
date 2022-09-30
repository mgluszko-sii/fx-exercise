package com.example.fxexercise.services;

import com.example.fxexercise.model.Price;
import com.example.fxexercise.repository.MessageRepository;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Setter
@NoArgsConstructor
public class PriceService {

    @Autowired
    private MessageRepository<Price> repository;
    @Autowired
    private PriceMarginApplier marginApplier;

    public Optional<Price> getPriceForInstrument(String instrumentName) {
        return repository.getItem(instrumentName);
    }

    public void addPrice(Price price) {
        Price marginedPrice = marginApplier.convert(price);
        repository.putItem(marginedPrice.getInstrument(), marginedPrice);
    }

}
