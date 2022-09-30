package com.example.fxexercise.repository;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@NoArgsConstructor
public class MessageRepositoryImpl implements MessageRepository<Price> {

    ConcurrentHashMap<Object, Price> cache = new ConcurrentHashMap<>();

    @Override
    public void putItemIfLatest(Price item) {
        cache.compute(item.getInstrument(), (key, val)
                -> val == null || item.getTimestamp().isAfter((val.getTimestamp())) ? item : val);
    }

    @Override
    public Optional<Price> getLatestItem(Object key) {
        return Optional.ofNullable(cache.get(key));
    }


}
