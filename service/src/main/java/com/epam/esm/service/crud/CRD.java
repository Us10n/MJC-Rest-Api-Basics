package com.epam.esm.service.crud;

import java.util.List;

/**
 * Interface of service that operates create, read, delete operations
 *
 * @param <T> Dto class
 * @param <R> Model class
 */
public interface CRD<T, R> {
    /**
     * Create T type object.
     *
     * @param object object to create instance of
     * @return the t
     */
    T create(T object);

    /**
     * Read all T type objects.
     *
     * @return list of existing objects
     */
    List<T> readAll();

    /**
     * Read T object by id.
     *
     * @param id object id
     * @return the T type object
     */
    T readById(long id);

    /**
     * Delete T object specified by id.
     *
     * @param id object id
     */
    void delete(long id);
}
