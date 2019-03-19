package com.epam.esm.repository.specification;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

public class SpecificationBuilder<T> implements Specification<T>{
    private final Queue<Specification<T>> specifications;

    public SpecificationBuilder(){
        specifications = new LinkedList<>();
    }

    public SpecificationBuilder(Queue<Specification<T>> specifications){
        this.specifications = specifications;
    }

    @Override
    public String toSqlClauses() {
        return specifications.stream()
                .map(Specification::toSqlClauses)
                .collect(Collectors.joining(" ")) + ";";
    }

    @Override
    public boolean specification(T t) {
        throw new UnsupportedOperationException();
    }

    public boolean addSpecification(Specification<T> specification) {
        return specifications.add(specification);
    }
}
