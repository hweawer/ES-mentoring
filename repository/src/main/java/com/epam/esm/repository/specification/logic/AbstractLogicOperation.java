package com.epam.esm.repository.specification.logic;

import com.epam.esm.repository.specification.Specification;

public abstract class AbstractLogicOperation implements Specification {
    protected Specification firstCondition;
    protected Specification secondCondition;

    public AbstractLogicOperation(Specification firstCondition, Specification secondCondition) {
        this.firstCondition = firstCondition;
        this.secondCondition = secondCondition;
    }
}
