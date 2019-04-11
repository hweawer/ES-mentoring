package com.epam.esm.repository.impl;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.repository.AbstractRepository;
import com.epam.esm.repository.OrderRepository;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public class OrderRepositoryImpl extends AbstractRepository<Order> implements OrderRepository {
    public OrderRepositoryImpl() {
        super(Order.class);
    }

    @Override
    public Stream<Order> findByUser(User user){
        final String ORDER_BY_USER = "select o from Order o join o.user u where u.id=:id";
        return entityManager.createQuery(ORDER_BY_USER, Order.class).setParameter("id", user.getId()).getResultStream();
    }
}
