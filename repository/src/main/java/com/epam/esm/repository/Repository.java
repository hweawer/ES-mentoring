package com.epam.esm.repository;

import com.epam.esm.repository.specification.Specification;

import java.util.List;

public interface Repository<T> {
    Long create(T t);
    Integer remove(T t);
    Integer update(T t);

    List<T> queryFromDatabase(Specification<T> specification);
    List<T> queryFromCollection(Specification<T> specification);
}
