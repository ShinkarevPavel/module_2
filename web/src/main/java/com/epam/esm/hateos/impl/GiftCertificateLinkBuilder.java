package com.epam.esm.hateos.impl;

import com.epam.esm.controller.GiftController;
import com.epam.esm.dto.GiftCertificateDto;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class GiftCertificateLinkBuilder extends AbstractBuilder<GiftCertificateDto>{

    private TagLinkBuilder tagLinkBuilder;

    public GiftCertificateLinkBuilder(TagLinkBuilder tagLinkBuilder) {
        this.tagLinkBuilder = tagLinkBuilder;
    }

    @Override
    public void addLinks(GiftCertificateDto giftCertificateDto) {
        addIdLinks(GiftController.class, giftCertificateDto, giftCertificateDto.getId(), GET);
        if (Objects.nonNull(giftCertificateDto.getTags()) && giftCertificateDto.getTags().size() > 0) {
            giftCertificateDto.getTags().forEach(tagDto -> tagLinkBuilder.addLinks(tagDto));
        }
    }
}
