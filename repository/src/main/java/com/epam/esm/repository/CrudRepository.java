package com.epam.esm.repository;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface CrudRepository<T> {
    void delete(T t);
    void create(T t);
    T update(T t);
    Stream<T> findAll(CriteriaQuery<T> specification, Integer page, Integer limit);
    Stream<T> findAll(Integer page, Integer limit);
    Optional<T> findById(Long id);
}
