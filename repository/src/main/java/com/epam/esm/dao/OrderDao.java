package com.epam.esm.dao;

import com.epam.esm.entity.Order;

import java.awt.print.Pageable;
import java.util.List;

public interface OrderDao extends BaseDao<Order> {
    List<Order> getUserOrders(Long userId);
}
