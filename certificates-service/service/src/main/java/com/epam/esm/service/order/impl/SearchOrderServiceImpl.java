package com.epam.esm.service.order.impl;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.RoleType;
import com.epam.esm.entity.User;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.dto.PageInfo;
import com.epam.esm.service.dto.PaginationInfoDto;
import com.epam.esm.service.dto.mapper.OrderMapper;
import com.epam.esm.service.order.OrderSearchService;
import com.epam.esm.service.validation.ValidationUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@AllArgsConstructor
@Service
public class SearchOrderServiceImpl implements OrderSearchService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

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
        Long orderCount = orderRepository.countOrdersByUser(user);
        Integer pageCount = Double.valueOf(Math.ceil(orderCount / limit)).intValue();
        ValidationUtil.checkPagination(page, pageCount);

        Set<RoleType> roles = user.getRoles().stream()
                .map(Role::getType)
                .collect(toSet());

        List<Order> orders = roles.contains(RoleType.ADMIN) ?
                orderRepository.findAll(page, limit).collect(toList()) :
                orderRepository.findByUser(page, limit, user).collect(toList());

        List<OrderDto> ordersDto = orders.stream()
                .map(OrderMapper.INSTANCE::toDto)
                .collect(toList());
        return  new PaginationInfoDto<>(ordersDto, new PageInfo(pageCount));
    }
}
