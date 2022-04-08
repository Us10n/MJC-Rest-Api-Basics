package by.us10n.service.crud;

public interface CRUD<T, R> extends CRD<T, R> {
    T update(T object);
}
