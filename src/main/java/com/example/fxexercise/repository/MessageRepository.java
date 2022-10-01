package com.example.fxexercise.repository;


import java.util.Optional;

public interface MessageRepository<T> {

    void putItemIfLatest(T item);
    Optional<T> getLatestItem(Object key);

    void clear();

}
