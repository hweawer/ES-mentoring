package com.epam.esm.service.impl;

import com.epam.esm.entity.*;
import com.epam.esm.repository.CrudRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.repository.impl.CertificateSnapshotRepositoryImpl;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.dto.mapper.OrderMapper;
import com.epam.esm.service.exception.IncorrectPaginationValues;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@RequiredArgsConstructor
@Service
public class OrderServiceDatabase implements OrderService {
    @NonNull
    private UserRepository userRepository;
    @NonNull
    private CrudRepository<GiftCertificate> certificateRepository;
    @NonNull
    private OrderRepository orderRepository;
    @NonNull
    private CertificateSnapshotRepositoryImpl snapshotRepository;

    @Transactional
    @Override
    public OrderDto orderCertificatesByUser(String username, List<Long> certificatesIds) {
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
        Double userCount = Double.valueOf(userRepository.count());
        if (page > userCount / limit){
            throw new IncorrectPaginationValues("");
        }
        User user = userRepository.findUserByLogin(username).orElseThrow(() -> new RuntimeException(""));
        Set<String> roles = user.getRoles().stream()
                                                .map(role -> role.getType().name())
                                                .collect(toSet());
        List<Order> orders = roles.contains(Role.ADMIN_ROLE) ?
                    orderRepository.findAll(page, limit).collect(toList()) :
                    orderRepository.findByUser(user).collect(toList());
        return orders.stream()
                    .map(OrderMapper.INSTANCE::toDto)
                    .collect(toList());
    }

}
