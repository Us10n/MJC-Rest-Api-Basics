package com.epam.esm.service.crud;

public interface CRUD<T, R> extends CRD<T, R> {
    T update(T object);
}
