package com.epam.esm.linkbuilder.impl;

import com.epam.esm.controller.GiftController;
import com.epam.esm.dto.GiftCertificateDto;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class GiftCertificateLinkBuilder extends AbstractBuilder<GiftCertificateDto>{

    private TagLinkBuilder tagLinkBuilder;

    public GiftCertificateLinkBuilder(TagLinkBuilder tagLinkBuilder) {
        this.tagLinkBuilder = tagLinkBuilder;
    }

    @Override
    public void addLinks(GiftCertificateDto giftCertificateDto) {
        addIdLinks(GiftController.class, giftCertificateDto, giftCertificateDto.getId(), SELF, UPDATE, DELETE);
        if (!CollectionUtils.isEmpty(giftCertificateDto.getTags())) {
            giftCertificateDto.getTags().forEach(tagDto -> tagLinkBuilder.addLinks(tagDto));
        }
    }
}
