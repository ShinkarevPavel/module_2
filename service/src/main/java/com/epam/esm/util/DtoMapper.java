package com.epam.esm.util;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
                .tags(giftCertificate.getTags() != null ? giftCertificate.getTags().stream().map(DtoMapper::TagToDto).collect(Collectors.toList()) : new ArrayList<>())
                .build();
    }

    public static GiftCertificate dtoToCertificate(GiftCertificateDto giftCertificateDto) {
        return GiftCertificate.builder()
                .id(giftCertificateDto.getId() != null ? giftCertificateDto.getId() : null)
                .name(giftCertificateDto.getName())
                .description(giftCertificateDto.getDescription())
                .price(giftCertificateDto.getPrice())
                .duration(giftCertificateDto.getDuration())
                .createDate(giftCertificateDto.getCreateDate())
                .lastUpdateDate(giftCertificateDto.getLastUpdateDate())
                .tags(giftCertificateDto.getTags() != null ? giftCertificateDto.getTags().stream().map(DtoMapper::dtoToTag).collect(Collectors.toList()) : new ArrayList<>())
                .build();
    }

    public static TagDto TagToDto(Tag tag) {
        TagDto tagDto = TagDto.builder()
                .id(tag.getId())
                .name(tag.getName())
                .build();
        return tagDto;
    }

    public static Tag dtoToTag(TagDto tagDto) {
        Tag tag = Tag.builder()
                .id(tagDto.getId() != null ? tagDto.getId() : null)
                .name(tagDto.getName())
                .build();
        return tag;
    }

    public static User dtoToUser(UserDto userDto) {
        User user = User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .orders(userDto.getOrders() != null ? userDto.getOrders().stream().map(DtoMapper::dtoToOrder).collect(Collectors.toList()) : new ArrayList<>())
                .build();
        return user;
    }

    public static UserDto userToDto(User user) {
        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .orders(user.getOrders() != null ? user.getOrders().stream().map(DtoMapper::orderToDto).collect(Collectors.toList()) : new ArrayList<>()).build();
        return userDto;
    }

    private static OrderDto orderToDto(Order order) {
        return null;
    }

    private static Order dtoToOrder(OrderDto orderDto) {
        return null;
    }
}