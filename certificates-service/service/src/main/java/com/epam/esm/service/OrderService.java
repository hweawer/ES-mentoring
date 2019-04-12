package com.epam.esm.service;

import com.epam.esm.service.dto.OrderDto;

import java.util.List;

public interface OrderService {
    OrderDto orderCertificatesByUser(String username, List<Long> certificatesIds);

    OrderDto findById(Long id);

    List<OrderDto> findByUser(Integer page, Integer limit, String username);
}
