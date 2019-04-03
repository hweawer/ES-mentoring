package com.epam.esm.repository;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

//todo: id return from create, do i need?
//todo: update method
public interface CrudRepository<T> {
    void delete(T t);
    void deleteById(Long id);
    void create(T t);
    T update(T t);
    Stream<T> findAll(CriteriaQuery<T> specification);
    Stream<T> findAll();
    T findById(Long id);
}
