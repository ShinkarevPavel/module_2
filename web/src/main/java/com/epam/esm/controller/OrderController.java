package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.linkbuilder.LinkBuilder;
import com.epam.esm.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public OrderDto create(@Valid @RequestBody OrderDto orderDto) {
        OrderDto oDto = orderService.create(orderDto);
        linkBuilder.addLinks(oDto);
        return oDto;
    }

    @GetMapping("/{orderId}")
    @PostAuthorize("returnObject.user.id.equals(authentication.principal.userId) || hasAuthority('ADMIN')")
    public OrderDto getById(@PathVariable Long orderId) {
        OrderDto orderDto = orderService.getById(orderId);
        linkBuilder.addLinks(orderDto);
        return orderDto;
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("authentication.principal.userId == #userId || hasAuthority('ADMIN')")
    public List<OrderDto> getOrderByUserId(@PathVariable Long userId) {
        List<OrderDto> orderDto = orderService.getUserOrders(userId);
        orderDto.forEach(linkBuilder::addLinks);
        return orderDto;
    }
}
