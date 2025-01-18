package org.example.plain.domain;

import org.springframework.stereotype.Repository;

import java.util.List;


public interface Dao<T> {
    List<T> findAll();
    T findById(String id);
    void save(T t);
    void update(T t);
    void delete(T t);
}
