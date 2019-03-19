package com.epam.esm.repository.specification.condition;

import com.epam.esm.repository.specification.Specification;

public abstract class ConditionSpecification<T> implements Specification<T> {
    String key;
    Object value;

    ConditionSpecification(String key, Object value){
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public boolean specification(T t) {
        throw new UnsupportedOperationException();
    }
}
