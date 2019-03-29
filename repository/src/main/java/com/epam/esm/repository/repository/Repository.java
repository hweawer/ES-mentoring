package com.epam.esm.repository.repository;

import com.epam.esm.repository.repository.specification.Specification;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {
    T create(T t);
    Integer delete(Long id);
    Integer update(T t);
    List<T> queryFromDatabase(Specification specification);
    List<T> findAll();
    Optional<T> findById(Long id);
}
