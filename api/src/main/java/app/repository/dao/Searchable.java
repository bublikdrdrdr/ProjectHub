package app.repository.dao;

import app.repository.etc.SearchParams;

import java.util.List;

public interface Searchable<E, S extends SearchParams> {

    List<E> search(S searchParams);
    long count(S searchParams);
}