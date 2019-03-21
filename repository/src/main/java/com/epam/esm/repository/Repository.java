package com.epam.esm.repository;

import com.epam.esm.repository.specification.Specification;

import java.util.Collection;
import java.util.List;

public interface Repository<T> {
    T create(T t);
    Integer remove(T t);
    Integer update(T t);

    List<T> queryFromDatabase(Specification specification);
    List<T> queryFromDatabase(Collection<Specification> specification);
}
