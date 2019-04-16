package com.epam.esm.repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
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
    EntityManager getEntityManager();
    Long count();
    Long count(CriteriaQuery<Long> query);
}
