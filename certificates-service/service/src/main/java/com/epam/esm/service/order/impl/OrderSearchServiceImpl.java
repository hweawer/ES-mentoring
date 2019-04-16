package com.epam.esm.service.order.impl;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.dto.PaginationDto;
import com.epam.esm.service.dto.TagDto;
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
    public PaginationDto<OrderDto> userOrders(Integer page, Integer limit, String username) {
        User user = userRepository.findUserByLogin(username);
        Double orderCount = orderRepository.countOrdersByUser(user).doubleValue();
        Integer pageCount = Double.valueOf(Math.ceil(orderCount / limit)).intValue();
        if (page > pageCount){
            throw new IncorrectPaginationValues("incorrect.pagination");
        }
        Set<String> roles = user.getRoles().stream()
                .map(role -> role.getType().name())
                .collect(toSet());
        List<Order> orders = roles.contains(Role.ADMIN_ROLE) ?
                orderRepository.findAll(page, limit).collect(toList()) :
                orderRepository.findByUser(page, limit, user).collect(toList());
        PaginationDto<OrderDto> paginationDto = new PaginationDto<>();
        paginationDto.setCollection(orders.stream()
                .map(OrderMapper.INSTANCE::toDto)
                .collect(toList()));
        paginationDto.setFirst("/orders?page=1&limit=" + limit);
        paginationDto.setLast("/orders?page=" + pageCount + "&limit=" + limit);
        String previous = page == 1 ? null : "/orders?page=" + (page - 1) + "&limit=" + limit;
        String next = page.equals(pageCount) ? null : "/orders?page=" + (page + 1) + "&limit=" + limit;
        paginationDto.setPrevious(previous);
        paginationDto.setNext(next);
        return paginationDto;
    }
}
