package com.example.fxexercise.services;

import com.example.fxexercise.repository.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class PriceMarginConverter implements PriceConverter {

    private final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;
    private final int scale;
    private final BigDecimal bidFee;
    private final BigDecimal askFee;

    @Autowired
    public PriceMarginConverter(@Value("${price.margin.scale}") int scale,
                                @Value("${price.margin.bid}") BigDecimal bidFee,
                                @Value("${price.margin.ask}") BigDecimal askFee){
        this.scale = scale;
        this.bidFee = bidFee;
        this.askFee = askFee;
    }

    public Price convert(Price price) {
        BigDecimal newBid = price.getBid()
                .subtract(price.getBid().multiply(bidFee))
                .setScale(scale, ROUNDING_MODE);
        price.setBid(newBid);

        BigDecimal newAsk = price.getAsk()
                .add((price.getAsk().multiply(askFee)))
                .setScale(scale, ROUNDING_MODE);
        price.setAsk(newAsk);

        return price;
    }
}
