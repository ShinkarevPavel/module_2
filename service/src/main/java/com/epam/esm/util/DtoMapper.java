package com.epam.esm.util;

import com.epam.esm.dto.*;
import com.epam.esm.entity.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class DtoMapper {

    public static GiftCertificateDto certificateToDto(GiftCertificate giftCertificate) {
        return GiftCertificateDto.builder()
                .id(giftCertificate.getId())
                .name(giftCertificate.getName())
                .description(giftCertificate.getDescription())
                .duration(giftCertificate.getDuration())
                .price(giftCertificate.getPrice())
                .createDate(giftCertificate.getCreateDate())
                .lastUpdateDate(giftCertificate.getLastUpdateDate())
                .tags(Objects.nonNull(giftCertificate.getTags()) ?
                        giftCertificate.getTags().stream().map(DtoMapper::TagToDto).collect(Collectors.toSet()) : new HashSet<>())
                .build();
    }

    public static GiftCertificate dtoToCertificate(GiftCertificateDto giftCertificateDto) {
        return GiftCertificate.builder()
                .id(Objects.nonNull(giftCertificateDto.getId()) ? giftCertificateDto.getId() : null)
                .name(giftCertificateDto.getName())
                .description(giftCertificateDto.getDescription())
                .price(giftCertificateDto.getPrice())
                .duration(giftCertificateDto.getDuration())
                .createDate(giftCertificateDto.getCreateDate())
                .lastUpdateDate(giftCertificateDto.getLastUpdateDate())
                .tags(Objects.nonNull(giftCertificateDto.getTags()) ?
                        giftCertificateDto.getTags().stream().map(DtoMapper::dtoToTag).collect(Collectors.toSet()) : new HashSet<>())
                .build();
    }

    public static TagDto TagToDto(Tag tag) {
        return TagDto.builder()
                .id(tag.getId())
                .name(tag.getName())
                .build();
    }

    public static Tag dtoToTag(TagDto tagDto) {
        return Tag.builder()
                .id(Objects.nonNull(tagDto.getId()) ? tagDto.getId() : null)
                .name(tagDto.getName())
                .build();
    }

    public static User dtoToUser(UserDto userDto) {
        return User.builder()
                .id(Objects.nonNull(userDto.getId()) ? userDto.getId() : null)
                .username(userDto.getUsername())
                .role(userDto.getRole())
                .build();
    }

    public static UserDto userToDto(User user) {
        return UserDto.builder()
                .id(Objects.nonNull(user.getId()) ? user.getId() : null)
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }

    public static OrderDto orderToDto(Order order) {
        return OrderDto.builder()
                .id(Objects.nonNull(order.getId()) ? order.getId() : null)
                .cost(order.getCost())
                .user(Objects.nonNull(order.getUser()) ?
                        DtoMapper.userToDto(order.getUser()) : null)
                .order_date(order.getOrderDate())
                .certificates(Objects.nonNull(order.getGiftCertificates()) ? order.getGiftCertificates()
                        .stream()
                        .map(DtoMapper::certificateToDto)
                        .collect(Collectors.toList()) : new ArrayList<>())
                .build();
    }

    public static Order dtoToOrder(OrderDto orderDto) {
        return Order.builder()
                .id(Objects.nonNull(orderDto.getId()) ? orderDto.getId() : null)
                .cost(orderDto.getCost())
                .user(Objects.nonNull(orderDto.getUser()) ? DtoMapper.dtoToUser(orderDto.getUser()) : null)
                .orderDate(orderDto.getOrder_date())
                .giftCertificates(Objects.nonNull(orderDto.getCertificates()) ? orderDto.getCertificates()
                        .stream()
                        .map(DtoMapper::dtoToCertificate)
                        .collect(Collectors.toList()) : new ArrayList<>())
                .build();
    }

    public static SearchParameter dtoToSearchParameter(SearchParameterDto searchParameterDto) {
        return SearchParameter.builder()
                .tagName(searchParameterDto.getTagName())
                .searchPart(searchParameterDto.getSearchPart())
                .fieldsForSort(searchParameterDto.getFieldsForSort())
                .orderSort(searchParameterDto.getOrderSort())
                .build();
    }

    public static PageParameter dtoToPageParameter(PageParameterDto pageParameterDto) {
        return PageParameter.builder()
                .page(pageParameterDto.getPage())
                .size(pageParameterDto.getSize())
                .build();
    }
}