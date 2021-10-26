package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.hateos.LinkBuilder;
import com.epam.esm.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v2/orders")
public class OrderController {

    private OrderService orderService;
    private LinkBuilder<OrderDto> linkBuilder;

    public OrderController(OrderService orderService, LinkBuilder<OrderDto> linkBuilder) {
        this.orderService = orderService;
        this.linkBuilder = linkBuilder;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto create(@Valid @RequestBody OrderDto orderDto) {
        OrderDto oDto = orderService.create(orderDto);
        linkBuilder.addLinks(oDto);
        return oDto;
    }

    @GetMapping("/{id}")
    public OrderDto getById(@PathVariable Long id) {
        OrderDto orderDto = orderService.getById(id);
        linkBuilder.addLinks(orderDto);
        return orderDto;
    }


    @GetMapping("/user/{id}")
    public List<OrderDto> getOrderByUserId(@PathVariable Long id) {
        List<OrderDto> orderDto = orderService.getUserOrders(id);
        orderDto.forEach(linkBuilder::addLinks);
        return orderDto;
    }
}
