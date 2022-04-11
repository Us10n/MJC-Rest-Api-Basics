package com.epam.esm.repository.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    Optional<T> create(T object);

    Optional<T> findById(long id);

    Optional<T> findByName(String name);

    List<T> findAll();

    void delete(long id);
}
