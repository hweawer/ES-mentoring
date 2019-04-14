package com.epam.esm.service.find.impl;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.dto.mapper.OrderMapper;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.IncorrectPaginationValues;
import com.epam.esm.service.find.FindOrderService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@RequiredArgsConstructor
@Service
public class FindOrderServiceImpl implements FindOrderService {
    @NonNull
    private UserRepository userRepository;
    @NonNull
    private OrderRepository orderRepository;

    @Transactional(readOnly = true)
    @Override
    public OrderDto findById(Long id) {
        return OrderMapper.INSTANCE.toDto(orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("order.not.found")));
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderDto> userOrders(Integer page, Integer limit, String username) {
        Double userCount = Double.valueOf(userRepository.count());
        Double div = userCount / limit;
        div = div % limit == 0 ? div : div + 1;
        if (page > div){
            throw new IncorrectPaginationValues("incorrect.pagination");
        }

        User user = userRepository.findUserByLogin(username)
                .orElseThrow(() -> new EntityNotFoundException("user.not.found"));
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
