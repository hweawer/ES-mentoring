package com.epam.esm.service.create;

import com.epam.esm.service.dto.OrderDto;

import java.util.List;

public interface CreateOrderService {
    OrderDto orderCertificatesByUser(String username, List<Long> certificatesIds);
}
