package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.DtoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private UserDao userDao;
    private OrderDao orderDao;
    private GiftCertificateDao giftCertificateDao;

    public OrderServiceImpl(UserDao userDao, OrderDao orderDao, GiftCertificateDao giftCertificateDao) {
        this.userDao = userDao;
        this.orderDao = orderDao;
        this.giftCertificateDao = giftCertificateDao;
    }

    @Override
    @Transactional
    // ToDo refactor method and implement sending all error messages
    public OrderDto create(OrderDto orderDto) {
        Order order = DtoMapper.dtoToOrder(orderDto);
        Optional<User> optionalUser = userDao.findById(order.getUser().getId());
        if (optionalUser.isEmpty()) {
            throw new NoSuchEntityException("error_message.user_not_found");
        }
        order.setUser(optionalUser.get());
        Double cost = 0.0;
        List<GiftCertificate> certificates = new ArrayList<>();
        for (GiftCertificate giftCertificate : order.getGiftCertificates()) {
            Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDao.findById(giftCertificate.getId());
            if (optionalGiftCertificate.isEmpty()) {
                throw new NoSuchEntityException("error_message.certificate_not_found");
            }
            certificates.add(optionalGiftCertificate.get());
            cost += optionalGiftCertificate.get().getPrice();
        }
        order.setGiftCertificates(certificates);
        order.setCost(cost);
        order = orderDao.create(order);
        return DtoMapper.orderToDto(order);
    }


    @Override
    public OrderDto getById(Long id) {
        return orderDao.findById(id).map(DtoMapper::orderToDto).orElseThrow(NoSuchEntityException::new);
    }

    @Override
    public List<OrderDto> getUserOrders(Long userId) {

        return orderDao.getUserOrders(userId)
                .stream()
                .map(DtoMapper::orderToDto)
                .collect(Collectors.toList());
    }
}
