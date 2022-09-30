package com.example.fxexercise.services;

import com.example.fxexercise.repository.Price;
import com.example.fxexercise.repository.MessageRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PriceService {

    @Autowired
    private MessageRepository<Price> repository;
    @Autowired
    private PriceMarginApplier marginApplier;

    public Optional<Price> getPriceForInstrument(String instrumentName) {
        return repository.getLatestItem(instrumentName);
    }

    public void addPrice(Price price) {
        Price marginedPrice = marginApplier.convert(price);
        repository.putItemIfLatest(marginedPrice);
    }

}
