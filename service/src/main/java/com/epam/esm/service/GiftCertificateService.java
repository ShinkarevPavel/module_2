package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.PageParameterDto;
import com.epam.esm.dto.SearchParameterDto;

import java.util.List;

public interface GiftCertificateService {

    GiftCertificateDto getById(long id);

    GiftCertificateDto create(GiftCertificateDto giftCertificateDto);

    void delete(long id);

    void update(GiftCertificateDto giftCertificateDto);

    List<GiftCertificateDto> findByAttributes(SearchParameterDto searchParameterDto, PageParameterDto pageParameterDto);
}
