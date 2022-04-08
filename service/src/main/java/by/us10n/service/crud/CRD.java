package by.us10n.service.crud;

import java.util.List;

public interface CRD<T, R> {
    T create(T object);
    List<T> createAll(List<T> objects);
    List<T> readAll();
    T readById(long id);
    void delete(long id);
}
