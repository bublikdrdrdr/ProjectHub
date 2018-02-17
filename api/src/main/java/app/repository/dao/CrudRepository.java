package app.repository.dao;

public interface CrudRepository<E> {

    E get(long id);
    long save(E e);
    void remove(E e);
}
