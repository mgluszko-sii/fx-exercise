package com.example.fxexercise.repository;


import java.util.Optional;

public interface MessageRepository<T> {

    void putItem(Object key, T item);
    Optional<T> getItem(Object key);
}
