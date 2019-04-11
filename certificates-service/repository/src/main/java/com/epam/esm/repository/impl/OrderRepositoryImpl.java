package com.epam.esm.repository.impl;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.repository.AbstractRepository;
import com.epam.esm.repository.OrderRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.stream.Stream;

@Repository
public class OrderRepositoryImpl extends AbstractRepository<Order> implements OrderRepository {
    public OrderRepositoryImpl() {
        super(Order.class);
    }

    //todo: JPQL
    @Override
    public Stream<Order> findByUser(User user){
        CriteriaQuery<Order> criteriaQuery = builder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        Join<Order, User> join = root.join("user_id");
        criteriaQuery.select(root);
        criteriaQuery.where(builder.equal(join.get("user_id"), user.getId()));
        TypedQuery<Order> query = entityManager.createQuery(criteriaQuery);
        return query.getResultStream();
    }
}
