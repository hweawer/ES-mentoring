package com.epam.esm.service.dto.mapper;

import com.epam.esm.entity.Order;
import com.epam.esm.service.dto.OrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderDto toDto(Order order);
    Order toEntity(OrderDto orderDto);
}