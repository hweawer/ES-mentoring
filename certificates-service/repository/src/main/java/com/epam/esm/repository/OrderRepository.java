package com.epam.esm.repository;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;

import java.util.Optional;
import java.util.stream.Stream;

public interface OrderRepository extends CrudRepository<Order>{
    Stream<Order> findByUser(Integer page, Integer limit, User user);
    Long countOrdersByUser(User user);
    Optional<Tag> mostPopularUserTag();
}
