package com.epam.esm.repository.impl;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.repository.AbstractRepository;
import com.epam.esm.repository.OrderRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class OrderRepositoryImpl extends AbstractRepository<Order> implements OrderRepository {
    public OrderRepositoryImpl() {
        super(Order.class);
    }

    @Override
    public void create(Order order) {
        order.setTimestamp(LocalDateTime.now());
        super.create(order);
    }

    @Override
    public Stream<Order> findByUser(Integer page, Integer limit, User user){
        final String ORDER_BY_USER = "select o from Order o join o.user u where u.id=:id";
        return entityManager.createQuery(ORDER_BY_USER, Order.class)
                .setParameter("id", user.getId())
                .setFirstResult((page - 1) * limit)
                .setMaxResults(limit)
                .getResultStream();
    }

    @Override
    public Long countOrdersByUser(User user){
        final String COUNT_USER_ORDERS = "select count(o) from Order o join o.user u where u.id=:id";
        return entityManager.createQuery(COUNT_USER_ORDERS, Long.class)
                .setParameter("id", user.getId())
                .getSingleResult();
    }

    @Override
    public Optional<Tag> mostPopularUserTag() {
        final String select = "select id, name from (select count(name) as count, name, tags.id as id from jes_dev.tags " +
                "inner join jes_dev.certificates_tags on tags.id = certificates_tags.tag_id " +
                "inner join " +
                "(select jes_dev.certificates_snapshots.certificate_id from (" +
                "        select jes_dev.orders.id from (" +
                "              select MAX(sum), user_id from (" +
                "                    select SUM(price) as sum, orders.user_id from jes_dev.users " +
                "                    inner join jes_dev.orders on users.id = orders.user_id " +
                "                    inner join jes_dev.certificates_snapshots on orders.id = certificates_snapshots.order_id " +
                "                    group by orders.user_id) as max" +
                "              group by user_id) as i2" +
                "  inner join jes_dev.orders on i2.user_id = orders.user_id) as i1 " +
                "  inner join jes_dev.certificates_snapshots on i1.id=certificates_snapshots.order_id) as i0 on i0.certificate_id=certificates_tags.certificate_id " +
                "group by (tags.id,name) " +
                "ORDER BY count DESC " +
                "LIMIT 1) as s";

        Query query = entityManager.createNativeQuery(select, Tag.class);
        List tags = query.getResultList();
        Optional<Tag> optional;
        if (tags.isEmpty()){
            optional = Optional.empty();
        }
        else optional = Optional.ofNullable((Tag) tags.get(0));
        return optional;
    }


}
