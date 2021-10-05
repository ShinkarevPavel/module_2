package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface GiftCertificateService {
    List<GiftCertificateDto> getAll();

    GiftCertificateDto getById(long id);

    GiftCertificateDto create(GiftCertificateDto giftCertificateDto);

    void delete(long id);

    void update(GiftCertificateDto giftCertificateDto);

    List<GiftCertificateDto> findByAttributes(String tagName, String searchPart, List<String> fieldsForSort, List<String> orderSort);

}
