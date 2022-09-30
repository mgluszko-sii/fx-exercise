package com.example.fxexercise.services;

import com.example.fxexercise.repository.Price;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@Setter
@NoArgsConstructor
public class PriceMarginApplier {

    private final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;
    @Value("${price.margin.scale}")
    private int scale;
    @Value("${price.margin.bid}")
    private BigDecimal bidFee;
    @Value("${price.margin.ask}")
    private BigDecimal askFee;

    public Price convert(Price price) {
        BigDecimal newBid = price.getBid()
                .subtract(price.getBid().multiply(bidFee))
                .setScale(scale, ROUNDING_MODE);
        BigDecimal newAsk = price.getAsk()
                .add((price.getAsk().multiply(askFee)))
                .setScale(scale, ROUNDING_MODE);
        price.setBid(newBid);
        price.setAsk(newAsk);

        return price;
    }
}
