package com.example.fxexercise.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class Price {
    private final String id;
    private final String instrument;
    private BigDecimal bid;
    private BigDecimal ask;
    private final LocalDateTime timestamp;
}
