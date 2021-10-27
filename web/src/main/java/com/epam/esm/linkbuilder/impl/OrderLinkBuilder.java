package com.epam.esm.linkbuilder.impl;

import com.epam.esm.controller.OrderController;
import com.epam.esm.dto.OrderDto;
import org.springframework.stereotype.Component;

@Component
public class OrderLinkBuilder extends AbstractBuilder<OrderDto>{

    private final UserLinkBuilder userLinkBuilder;
    private GiftCertificateLinkBuilder certificateLinkBuilder;

    public OrderLinkBuilder(UserLinkBuilder userLinkBuilder, GiftCertificateLinkBuilder certificateLinkBuilder) {
        this.userLinkBuilder = userLinkBuilder;
        this.certificateLinkBuilder = certificateLinkBuilder;
    }

    @Override
    public void addLinks(OrderDto orderDto) {
        addIdLinks(OrderController.class, orderDto, orderDto.getId(), GET, UPDATE, DELETE);
        userLinkBuilder.addLinks(orderDto.getUser());
        orderDto.getCertificates().forEach(certificateLinkBuilder::addLinks);
    }
}
