package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private UserDao userDao;
    private OrderDao orderDao;

    public OrderServiceImpl(UserDao userDao, OrderDao orderDao) {
        this.userDao = userDao;
        this.orderDao = orderDao;
    }

    @Override
    public OrderDto create(OrderDto orderDto) {
//        throw new NoSuchEntityException("error_message.user_not_found");

        return null;
    }

    @Override
    public List<OrderDto> getUserOrders(Long userId) {
        return null;
    }
}
