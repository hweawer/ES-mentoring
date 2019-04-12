package com.epam.esm.service.update.property.certificate;

public interface UpdateProperty<T, V> {
    boolean update(T entity, V value);
}
