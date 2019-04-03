package com.epam.esm.repository;

import com.epam.esm.repository.exception.EntityNotFoundException;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public abstract class AbstractRepository<T> implements CrudRepository<T> {

    @PersistenceContext
    protected EntityManager entityManager;
    protected CriteriaBuilder builder;
    private Class<T> entity;

    public AbstractRepository(Class<T> tClass){
        entity = tClass;
    }

    @PostConstruct
    public void init(){
        builder = entityManager.getCriteriaBuilder();
    }

    @Override
    public void create(T t){
        entityManager.persist(t);
    }

    @Override
    public void delete(T t) {
        entityManager.remove(t);
    }

    @Override
    public Stream<T> findAll(CriteriaQuery<T> specification) {
        TypedQuery<T> query = entityManager.createQuery(specification);
        return query.getResultStream();
    }

    @Override
    public Stream<T> findAll() {
        CriteriaQuery<T> criteriaQuery = builder.createQuery(entity);
        Root<T> root = criteriaQuery.from(entity);
        criteriaQuery.select(root);
        TypedQuery<T> query = entityManager.createQuery(criteriaQuery);
        return query.getResultStream();
    }

    //todo: change localization message
    @Override
    public T findById(Long id) {
        return Optional.ofNullable(entityManager.find(entity, id))
                .orElseThrow(() -> new EntityNotFoundException("tag.not.found.by.id"));
    }

    @Override
    public void deleteById(Long id) {
        T entity = findById(id);
        delete(entity);
    }
}
