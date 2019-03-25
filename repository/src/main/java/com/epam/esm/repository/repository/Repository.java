package com.epam.esm.repository.repository;

import com.epam.esm.repository.repository.specification.Specification;

import java.util.List;

public interface Repository<T> {
    T create(T t);
    Integer delete(Long id);
    Integer update(T t);
    List<T> queryFromDatabase(Specification specification);
    List<T> findAll();
    List<T> findById(Long id);
}
