package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;

import java.util.List;

public interface OrderService {

    OrderDto create(OrderDto orderDto);

    List<OrderDto> getUserOrders(Long userId);

}
