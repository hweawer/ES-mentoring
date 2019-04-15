package com.epam.esm.service.certificate.update.property;

public interface UpdateProperty<T, V> {
    boolean update(T entity, V value);
}
