package com.epam.esm.repository.dao;

import java.util.List;
import java.util.Optional;

/**
 * Data access object that performs operations with databases
 *
 * @param <T> define model DAO operates with
 * @see GiftCertificateDao,TagDao
 */
public interface Dao<T> {
    /**
     * Create {@link T} object instance in database.
     *
     * @param object object to create in database
     * @return {@link Optional Optional.ofNullable()} of created object
     */
    Optional<T> create(T object);

    /**
     * Find object instance in database by id.
     *
     * @param id object id
     * @return {@link Optional Optional.ofNullable()} of found object.
     */
    Optional<T> findById(long id);

    /**
     * Find object instance in database by name.
     *
     * @param name object name
     * @return {@link Optional Optional.ofNullable()} of found object.
     */
    Optional<T> findByName(String name);

    /**
     * Find all object instances in database.
     *
     * @return list of found objects
     */
    List<T> findAll();

    /**
     * Delete object instance in database by id.
     *
     * @param id objects id
     */
    void delete(long id);
}
