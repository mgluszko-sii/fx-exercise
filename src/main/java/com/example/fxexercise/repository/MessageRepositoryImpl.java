package com.example.fxexercise.repository;

import com.example.fxexercise.model.Price;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Setter
@NoArgsConstructor
public class MessageRepositoryImpl implements MessageRepository<Price> {

    ConcurrentHashMap<Object, Price> cache = new ConcurrentHashMap<>();

    @Override
    public void putItem(Object key, Price item) {
        Price currentValue = cache.get(key);
        if(item.getTimestamp().isAfter(currentValue.getTimestamp())){
            cache.put(key, item);
        }
    }

    @Override
    public Optional<Price> getItem(Object key) {
        return Optional.ofNullable(cache.get(key));
    }

}
