package com.epam.esm.repository.impl;

import com.epam.esm.entity.Order;
import com.epam.esm.repository.AbstractRepository;
import com.epam.esm.repository.OrderRepository;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepositoryImpl extends AbstractRepository<Order> implements OrderRepository {
    public OrderRepositoryImpl() {
        super(Order.class);
    }
}
