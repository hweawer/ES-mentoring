package com.epam.esm.service.update;

public interface UpdateEntity<K, T> {
    T update(K key, T dto);
}
