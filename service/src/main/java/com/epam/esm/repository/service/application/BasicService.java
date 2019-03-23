package com.epam.esm.service.application;

import java.util.List;

public interface BasicService<T> {
    Long create(T t);
    Integer remove(T t);
    Integer update(T t);
    List<T> findAll();
    T findById(Long id);
}
