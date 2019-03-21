package com.epam.esm.repository.specification.condition;

import com.epam.esm.repository.specification.Specification;

public abstract class AbstractConditionSpecification implements Specification {
    protected Object key;
    protected Object value;

    AbstractConditionSpecification(Object key, Object value){
        this.key = key;
        this.value = value;
    }

    public Object getKey() {
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
}
