package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.BaseService;
import com.epam.esm.util.GiftCertificateConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Component
@Builder
@AllArgsConstructor
public class GiftCertificateServiceImpl implements BaseService {

    private GiftCertificateDao giftCertificateDao;
    private TagDao tagDao;

    @Override
    public List<GiftCertificateDto> getAll() {
        List<GiftCertificate> giftCertificateList = giftCertificateDao.findAll();
        List<GiftCertificateDto> giftCertificateDTOList = entityToDto(giftCertificateList);
        return giftCertificateDTOList;
    }

    private List<GiftCertificateDto> entityToDto(List<GiftCertificate> certificates) { //Todo sent to UTIL class !!!
        List<GiftCertificateDto> dtoCertificates = certificates.stream()
                .map(GiftCertificateConverter::toDto)
                .collect(Collectors
                        .toList());
        return dtoCertificates;
    }

    @Override
    public GiftCertificateDto getById(long id) {
        GiftCertificate certificate = giftCertificateDao.findById(id).orElseThrow();
        return GiftCertificateConverter.toDto(certificate);
    }

    @Transactional
    @Override
    public GiftCertificateDto create(GiftCertificateDto giftCertificateDto) {
        GiftCertificate certificate = GiftCertificateConverter.toEntity(giftCertificateDto);
        certificate = giftCertificateDao.create(certificate);
        certificate.setTags(tagDao.addCertificateTags(certificate.getTags()));
        giftCertificateDao.addToAssociateTable(certificate.getId(), certificate.getTags());
        return GiftCertificateConverter.toDto(certificate);
    }
}
