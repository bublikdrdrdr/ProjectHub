package app.repository.dao.implementation;

@FunctionalInterface
public interface FieldsInitializer<E> {

    void init(E entity);
}
