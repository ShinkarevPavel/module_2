package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = OrderServiceImpl.class)
class OrderServiceImplTest {
    private static final Long FIRST_ORDER_ID = 1L;
    private static final Long SECOND_ORDER_ID = 2L;
    private static final Double FIRST_ORDER_COST = 123.40;
    private static final Double SECOND_ORDER_COST = 1123.40;
    private Order firstOrder;
    private Order secondOrder;
    private List<GiftCertificate> certificates;
    private OrderDto firstOrderDto;
    private OrderDto secondOrderDto;
    private List<GiftCertificateDto> certificateDto;
    private List<Order> orders;
    private List<OrderDto> orderDtoList;


    @Autowired
    private OrderService orderService;

    @MockBean
    private User user;

    @MockBean
    private UserDto userDto;

    @MockBean(name = "certificate1")
    private GiftCertificate giftCertificate;

    @MockBean(name = "certificate2")
    private GiftCertificate secondGiftCertificate;

    @MockBean(name = "dto1")
    private GiftCertificateDto firstGiftCertificateDto;

    @MockBean(name = "dto2")
    private GiftCertificateDto secondGiftCertificateDto;

    @MockBean
    private UserDao userDao;

    @MockBean
    private GiftCertificateDao giftCertificateDao;

    @MockBean
    private OrderDao orderDao;

    @BeforeEach
    void prepare() {
        certificates = new ArrayList<>();
        certificates.add(giftCertificate);
        certificates.add(secondGiftCertificate);

        certificateDto = new ArrayList<>();
        certificateDto.add(firstGiftCertificateDto);
        certificateDto.add(secondGiftCertificateDto);

        firstOrder = Order.builder()
                .id(FIRST_ORDER_ID)
                .cost(FIRST_ORDER_COST)
                .orderDate(LocalDateTime.now())
                .user(user)
                .giftCertificates(certificates)
                .build();

        secondOrder = Order.builder()
                .id(SECOND_ORDER_ID)
                .cost(SECOND_ORDER_COST)
                .orderDate(LocalDateTime.now())
                .user(user)
                .giftCertificates(certificates)
                .build();
        firstOrderDto = OrderDto.builder()
                .cost(FIRST_ORDER_COST)
                .order_date(LocalDateTime.now())
                .user(userDto)
                .certificates(certificateDto)
                .build();
        secondOrderDto = OrderDto.builder()
                .cost(SECOND_ORDER_COST)
                .order_date(LocalDateTime.now())
                .user(userDto)
                .certificates(certificateDto)
                .build();

        orders = new ArrayList<>();
        orders.add(firstOrder);
        orders.add(secondOrder);

        orderDtoList = new ArrayList<>();
        orderDtoList.add(firstOrderDto);
        orderDtoList.add(secondOrderDto);
    }

    @Test
    void create() {
        when(userDao.findById(anyLong())).thenReturn(Optional.of(user));
        when(giftCertificateDao.findById(anyLong())).thenReturn(Optional.of(giftCertificate));
        when(orderDao.create(any())).thenReturn(firstOrder);
        OrderDto actual = orderService.create(firstOrderDto);
        assertEquals(actual.getId(), firstOrder.getId());
        assertEquals(actual.getCost(), firstOrder.getCost());
    }

    @Test
    void getById() {
        when(orderDao.findById(FIRST_ORDER_ID)).thenReturn(Optional.of(firstOrder));
        OrderDto actual = orderService.getById(FIRST_ORDER_ID);
        assertEquals(actual.getId(), firstOrder.getId());
    }

    @Test
    void getUserOrders() {
        when(userDao.isContains(anyLong())).thenReturn(true);
        when(orderDao.getUserOrders(anyLong())).thenReturn(orders);
        List<OrderDto> actual = orderService.getUserOrders(user.getId());
        assertEquals(actual.size(), orders.size());
    }

    @Test
    void getUserOrdersExpectNoSuchEntityException() {
        when(userDao.isContains(anyLong())).thenReturn(false);
        assertThrows(NoSuchEntityException.class, () -> orderService.getUserOrders(user.getId()));
    }
}