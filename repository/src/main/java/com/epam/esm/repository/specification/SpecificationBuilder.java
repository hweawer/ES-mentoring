package com.epam.esm.repository.specification;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Queue;

public class SpecificationBuilder {
    private final Queue<Specification> specifications;

    public SpecificationBuilder(){
        specifications = new ArrayDeque<>();
    }

    public SpecificationBuilder(Queue<Specification> specifications){
        this.specifications = specifications;
    }

    public Collection<Specification> getSpecifications() {
        return specifications;
    }

    public boolean addSpecification(Specification specification) {
        return specifications.add(specification);
    }
}
