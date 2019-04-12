package com.epam.esm.service.find;

import com.epam.esm.service.dto.OrderDto;

import java.util.List;

public interface FindOrderService {
    OrderDto findById(Long id);

    List<OrderDto> userOrders(Integer page, Integer limit, String username);
}
