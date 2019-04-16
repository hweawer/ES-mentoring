package com.epam.esm.service.order.impl;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.dto.*;
import com.epam.esm.service.dto.mapper.OrderMapper;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.IncorrectPaginationValues;
import com.epam.esm.service.order.OrderSearchService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@AllArgsConstructor
@Service
public class OrderSearchServiceImpl implements OrderSearchService {
    private UserRepository userRepository;
    private OrderRepository orderRepository;

    @Transactional(readOnly = true)
    @Override
    public OrderDto findById(Long id) {
        return OrderMapper.INSTANCE.toDto(orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("order.not.found")));
    }

    @Transactional(readOnly = true)
    @Override
    public PaginationInfoDto<OrderDto> userOrders(Integer page, Integer limit, String username) {
        User user = userRepository.findUserByLogin(username);
        Double orderCount = orderRepository.countOrdersByUser(user).doubleValue();
        Integer pageCount = Double.valueOf(Math.ceil(orderCount / limit)).intValue();
        if (page > pageCount && pageCount != 0){
            throw new IncorrectPaginationValues("incorrect.pagination");
        }
        Set<String> roles = user.getRoles().stream()
                .map(role -> role.getType().name())
                .collect(toSet());
        List<Order> orders = roles.contains(Role.ADMIN_ROLE) ?
                orderRepository.findAll(page, limit).collect(toList()) :
                orderRepository.findByUser(page, limit, user).collect(toList());
        PaginationInfoDto<OrderDto> paginationInfoDto = new PaginationInfoDto<>();
        paginationInfoDto.setCollection(orders.stream()
                .map(OrderMapper.INSTANCE::toDto)
                .collect(toList()));
        PageInfo pageInfo = new PageInfo(pageCount);
        paginationInfoDto.setPageInfo(pageInfo);
        return paginationInfoDto;
    }
}
