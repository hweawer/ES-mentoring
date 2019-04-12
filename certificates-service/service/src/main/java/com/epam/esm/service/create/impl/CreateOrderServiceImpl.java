package com.epam.esm.service.create.impl;

import com.epam.esm.entity.CertificateSnapshot;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.repository.*;
import com.epam.esm.service.create.CreateOrderService;
import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.dto.mapper.OrderMapper;
import com.epam.esm.service.exception.NotEnoughMoneyException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class CreateOrderServiceImpl implements CreateOrderService {
    @NonNull
    private UserRepository userRepository;
    @NonNull
    private CertificatesRepository certificateRepository;
    @NonNull
    private SnapshotRepository snapshotRepository;
    @NonNull
    private OrderRepository orderRepository;

    @Transactional
    @Override
    public OrderDto orderCertificatesByUser(String username, List<Long> certificatesIds) {
        User user = userRepository.findUserByLogin(username)
                .orElseThrow(() -> new RuntimeException(""));
        List<CertificateSnapshot> snapshots = certificatesIds.stream()
                .map(id -> certificateRepository.findById(id).orElseThrow(() -> new RuntimeException("")))
                .map(CertificateSnapshot::new)
                .collect(toList());
        BigDecimal sum = snapshots.stream()
                                            .map(CertificateSnapshot::getPrice)
                                            .reduce(BigDecimal::add)
                                            .orElseThrow(() -> new RuntimeException(""));
        BigDecimal money = user.getMoney();
        if (money.compareTo(sum) < 0){
            throw new NotEnoughMoneyException("");
        }
        user.setMoney(money.subtract(sum));

        Order order = new Order();
        order.setUser(user);
        snapshots.forEach(snapshotRepository::create);
        order.setCertificates(snapshots);
        orderRepository.create(order);

        return OrderMapper.INSTANCE.toDto(order);
    }
}
