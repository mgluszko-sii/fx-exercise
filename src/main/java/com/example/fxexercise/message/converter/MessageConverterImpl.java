package com.example.fxexercise.message.converter;

import com.example.fxexercise.repository.Price;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@NoArgsConstructor
public class MessageConverterImpl implements MessageConverter<String, Price>{

    @Setter
    @Value("${priceConverter.separator}")
    private String separator;

    private DateTimeFormatter formatter;


    @Override
    public Price convert(String message) {
        try {
            String[] record = message.split(separator);
            return Price.builder()
                    .id(record[0])
                    .instrument(record[1])
                    .bid(new BigDecimal(record[2]))
                    .ask(new BigDecimal(record[3]))
                    .timestamp(LocalDateTime.parse(record[4], formatter))
                    .build();
        }
        catch(Exception e) {
            throw new ConversionException("Failed to convert message: " + message);
        }
    }

    @Autowired
    public void setFormatter(@Value("${priceConverter.formatter}") String pattern) {
        this.formatter = DateTimeFormatter.ofPattern(pattern);
    }
}
