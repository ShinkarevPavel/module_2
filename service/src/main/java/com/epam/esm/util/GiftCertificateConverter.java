package com.epam.esm.util;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import lombok.Builder;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Builder
@Component
public class GiftCertificateConverter { // Todo Rename to DtoMapper and transfer TadConverter here

    public static GiftCertificateDto toDto(GiftCertificate giftCertificate) {
        return GiftCertificateDto.builder()
                .id(giftCertificate.getId())
                .name(giftCertificate.getName())
                .description(giftCertificate.getDescription())
                .duration(giftCertificate.getDuration())
                .price(giftCertificate.getPrice())
                .createDate(giftCertificate.getCreateDate())
                .lastUpdateDate(giftCertificate.getLastUpdateDate())
                .tags(giftCertificate.getTags().stream().map(TagConverter::toDto).collect(Collectors.toList()))
                .build();
    }

    public static GiftCertificate toEntity(GiftCertificateDto giftCertificateDto) {
        return GiftCertificate.builder()
                .id(giftCertificateDto.getId() != null ? giftCertificateDto.getId() : null)
                .name(giftCertificateDto.getName())
                .description(giftCertificateDto.getDescription())
                .price(giftCertificateDto.getPrice())
                .duration(giftCertificateDto.getDuration())
                .createDate(giftCertificateDto.getCreateDate())
                .lastUpdateDate(giftCertificateDto.getLastUpdateDate())
                .tags(giftCertificateDto.getTags().stream().map(TagConverter::toEntity).collect(Collectors.toList()))
                .build();
    }
}