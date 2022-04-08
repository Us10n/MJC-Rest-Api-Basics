package by.us10n.repository.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    Optional<T> create(T object);

    Optional<T> findById(long id);

    List<T> findAll();

    void delete(long id);
}
