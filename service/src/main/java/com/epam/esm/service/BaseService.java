package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;

import java.util.List;

public interface BaseService {
    List<GiftCertificateDto> getAll();
    GiftCertificateDto getById(long id);
    GiftCertificateDto create(GiftCertificateDto giftCertificateDto);

}
