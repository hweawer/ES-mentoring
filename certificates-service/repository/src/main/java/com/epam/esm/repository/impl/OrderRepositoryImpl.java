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
    public Stream<Order> findByUser(User user){
        final String ORDER_BY_USER = "select o from Order o join o.user u where u.id=:id";
        return entityManager.createQuery(ORDER_BY_USER, Order.class).setParameter("id", user.getId()).getResultStream();
    }

    @Override
    public Optional<Tag> mostPopularUserTag(User user) {
        final String select = "select id, name from (select count(name) as count, name, tags.id as id from jes_dev.tags " +
                "inner join jes_dev.certificates_tags on tags.id = certificates_tags.tag_id " +
                "inner join " +
                "(select jes_dev.certificates_snapshots.id from (" +
                "        select jes_dev.orders.id from (" +
                "              select MAX(sum), user_id from (" +
                "                    select SUM(price) as sum, user_id from jes_dev.users " +
                "                    inner join jes_dev.orders on users.id = orders.user_id " +
                "                    inner join jes_dev.certificates_snapshots on orders.id = certificates_snapshots.order_id " +
                "                    group by user_id" +
                "                    having user_id=?) as max" +
                "              group by user_id) as i2" +
                "              inner join jes_dev.orders on i2.user_id = orders.user_id) as i1\n" +
                "  inner join jes_dev.certificates_snapshots on i1.id=certificates_snapshots.order_id) as i0 on i0.id=certificate_id " +
                "group by (tags.id,name) " +
                "ORDER BY count DESC " +
                "LIMIT 1) as s";

        Query query = entityManager.createNativeQuery(select, Tag.class);
        query.setParameter(1, user.getId());
        List tags = query.getResultList();
        Optional<Tag> optional;
        if (tags.isEmpty()){
            optional = Optional.empty();
        }
        else optional = Optional.ofNullable((Tag) tags.get(0));
        return optional;
    }


}
