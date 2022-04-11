package com.epam.esm.service.crud;

import java.util.List;

public interface CRD<T, R> {
    T create(T object);
    List<T> readAll();
    T readById(long id);
    void delete(long id);
}