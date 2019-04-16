package com.epam.esm.service.order;

import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.dto.PaginationDto;

import java.util.List;

public interface OrderSearchService {
    OrderDto findById(Long id);

    PaginationDto<OrderDto> userOrders(Integer page, Integer limit, String username);
}
