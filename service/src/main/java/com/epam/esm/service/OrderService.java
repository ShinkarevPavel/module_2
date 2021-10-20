package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.Order;

import java.util.List;

public interface OrderService {

    OrderDto getById(Long id);

    OrderDto create(OrderDto orderDto);

    List<OrderDto> getUserOrders(Long userId);

}
