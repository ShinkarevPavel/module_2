package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;

import java.util.List;

public interface GiftCertificateService {

    GiftCertificateDto getById(long id);

    GiftCertificateDto create(GiftCertificateDto giftCertificateDto);

    void delete(long id);

    void update(GiftCertificateDto giftCertificateDto) throws IllegalAccessException;

    List<GiftCertificateDto> findByAttributes(List<String> tagName, String searchPart, List<String> fieldsForSort, List<String> orderSort);

}
