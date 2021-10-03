package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.DtoMapper;
import com.epam.esm.util.CertificateFieldOnNullChecker;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Builder
@AllArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private GiftCertificateDao giftCertificateDao;
    private TagDao tagDao;
    private CertificateFieldOnNullChecker nullChecker;

    @Override
    public List<GiftCertificateDto> getAll() {
        List<GiftCertificate> giftCertificateList = giftCertificateDao.findAll();
        return giftCertificateList.stream()
                .map(DtoMapper::CertificateToDto)
                .collect(Collectors
                        .toList());
    }

    @Override
    public GiftCertificateDto getById(long id) {
        GiftCertificate certificate = giftCertificateDao.findById(id).orElseThrow();
        return DtoMapper.CertificateToDto(certificate);
    }


    @Transactional
    @Override
    public GiftCertificateDto create(GiftCertificateDto giftCertificateDto) {
        GiftCertificate certificate = DtoMapper.dtoToCertificate(giftCertificateDto);
        certificate = giftCertificateDao.create(certificate);
        certificate.setTags(tagDao.addCertificateTags(certificate.getTags()));
        giftCertificateDao.addToAssociateTable(certificate.getId(), certificate.getTags());
        return DtoMapper.CertificateToDto(certificate);
    }

    @Transactional
    @Override
    public void delete(long id) {
        giftCertificateDao.deleteFromAssociateTable(id);
        giftCertificateDao.delete(id);
    }

    @Transactional
    @Override
    public GiftCertificateDto update(GiftCertificateDto giftCertificateDto) {
        Map<String, Object> notNullField = nullChecker.fieldCheck(giftCertificateDto);
        List<Tag> certificateNewTags = nullChecker.certificateTagsChecker(giftCertificateDto.getTags(), giftCertificateDto.getId());
        giftCertificateDao.addToAssociateTable(giftCertificateDto.getId(), certificateNewTags);
        GiftCertificate giftCertificate = giftCertificateDao.update(notNullField, giftCertificateDto.getId());
        return DtoMapper.CertificateToDto(giftCertificate);
    }
}
