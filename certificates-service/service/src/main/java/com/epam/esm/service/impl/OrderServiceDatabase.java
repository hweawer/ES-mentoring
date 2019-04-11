package com.epam.esm.service.impl;

import com.epam.esm.entity.CertificateSnapshot;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.repository.CrudRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.repository.impl.CertificateSnapshotRepositoryImpl;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.dto.OrderMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Service
public class OrderServiceDatabase implements OrderService {
    private static final String ROLE_ADMIN = "ADMIN";

    private UserRepository userRepository;
    private CrudRepository<GiftCertificate> certificateRepository;
    private OrderRepository orderRepository;
    private CertificateSnapshotRepositoryImpl snapshotRepository;

    public OrderServiceDatabase(UserRepository userRepository,
                                CrudRepository<GiftCertificate> certificateRepository,
                                OrderRepository orderRepository,
                                CertificateSnapshotRepositoryImpl snapshotRepository){
        this.userRepository = userRepository;
        this.certificateRepository = certificateRepository;
        this.orderRepository = orderRepository;
        this.snapshotRepository = snapshotRepository;
    }

    @Transactional
    @Override
    public OrderDto create(String username, List<Long> certificatesIds) {
        User user = userRepository.findUserByLogin(username).orElseThrow(() -> new RuntimeException(""));
        List<GiftCertificate> certificates = certificatesIds.stream()
                                                            .map(id -> certificateRepository.findById(id).orElseThrow(() -> new RuntimeException("")))
                                                            .collect(toList());
        Order order = new Order();
        order.setUser(user);
        List<CertificateSnapshot> snapshots = certificates.stream()
                .map(CertificateSnapshot::new)
                .collect(toList());
        snapshots.forEach(snapshotRepository::create);
        order.setCertificates(snapshots);
        order.setTimestamp(LocalDateTime.now());
        orderRepository.create(order);
        return OrderMapper.INSTANCE.toDto(order);
    }

    @Transactional(readOnly = true)
    @Override
    public OrderDto findById(Long id) {
        return OrderMapper.INSTANCE.toDto(orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("")));
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderDto> findByUser(Integer page, Integer limit, String username) {
        User user = userRepository.findUserByLogin(username).orElseThrow(() -> new RuntimeException(""));
        Set<String> roles = user.getRoles().stream()
                                                .map(role -> role.getType().name())
                                                .collect(toSet());
        List<Order> orders = roles.contains(ROLE_ADMIN) ?
                    orderRepository.findAll(page, limit).collect(toList()) :
                    orderRepository.findByUser(user).collect(toList());
        return orders.stream()
                    .map(OrderMapper.INSTANCE::toDto)
                    .collect(toList());
    }

}