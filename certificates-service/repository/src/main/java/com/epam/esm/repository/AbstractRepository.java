package com.epam.esm.repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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

    //todo: JPQL
    @Override
    public Stream<T> findAll(CriteriaQuery<T> specification, Integer page, Integer limit) {
        TypedQuery<T> query = entityManager.createQuery(specification);
        query.setFirstResult((page - 1) * limit);
        query.setMaxResults(limit);
        return query.getResultStream();
    }

    //todo: JPQL
    @Override
    public Stream<T> findAll(Integer page, Integer limit) {
        CriteriaQuery<T> select = builder.createQuery(entity);
        Root<T> from = select.from(entity);
        select.select(from);
        return findAll(select, page, limit);
    }

    @Override
    public Optional<T> findById(Long id) {
        return Optional.ofNullable(entityManager.find(entity, id));
    }

    @Override
    public T update(T t) {
        return entityManager.merge(t);
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}
