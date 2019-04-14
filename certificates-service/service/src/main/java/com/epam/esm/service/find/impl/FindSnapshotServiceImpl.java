package com.epam.esm.service.find.impl;

import com.epam.esm.entity.Order;
import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.dto.SnapshotDto;
import com.epam.esm.service.find.FindOrderService;
import com.epam.esm.service.find.FindSnapshotService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class FindSnapshotServiceImpl implements FindSnapshotService {
    @NonNull
    private FindOrderService findOrderService;

    @Override
    public List<SnapshotDto> findUserCertificates(Integer page, Integer limit, String username) {
        List<OrderDto> orders = findOrderService.userOrders(page, limit, username);
        return orders.stream()
                        .flatMap(order -> order.getCertificates().stream())
                        .collect(toList());
    }
}
