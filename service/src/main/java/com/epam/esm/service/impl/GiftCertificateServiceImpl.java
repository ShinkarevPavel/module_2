package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityFieldValidationException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.DtoMapper;
import com.epam.esm.validator.EntityValidator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.epam.esm.dao.rowmapper.GiftCertificateMapper.*;

@Service
@Builder
@AllArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private GiftCertificateDao giftCertificateDao;
    private TagDao tagDao;
    private TagCertificateDao tagCertificateDao;

    @Override
    public List<GiftCertificateDto> getAll() {
        List<GiftCertificate> giftCertificateList = giftCertificateDao.findAll();
        return giftCertificateList.stream()
                .map(DtoMapper::certificateToDto)
                .collect(Collectors.toList());
    }

    @Override
    public GiftCertificateDto getById(long id) throws NoSuchEntityException{
        return giftCertificateDao.findById(id)
                .map(DtoMapper :: certificateToDto)
                .orElseThrow(NoSuchEntityException::new);
    }


    @Transactional
    @Override
    public GiftCertificateDto create(GiftCertificateDto giftCertificateDto) {
        GiftCertificate certificate = DtoMapper.dtoToCertificate(giftCertificateDto);
        certificate = giftCertificateDao.create(certificate);
        certificate.setTags(tagDao.addCertificateTags(certificate.getTags()));
        tagCertificateDao.addToTagCertificateAssociateTable(certificate.getId(), certificate.getTags());
        return DtoMapper.certificateToDto(certificate);
    }

    @Transactional
    @Override
    public void delete(long id) {
        tagCertificateDao.deleteFromTagCertificateAssociateTable(id);
        giftCertificateDao.delete(id);
    }

    @Transactional
    @Override
    public void update(GiftCertificateDto giftCertificateDto) throws NoSuchEntityException{
        if (!giftCertificateDao.findById(giftCertificateDto.getId()).isPresent() || giftCertificateDto.getId() == null) {
            throw new NoSuchEntityException();
        }
        Map<String, Object> notNullField = fieldValidator(giftCertificateDto);
        List<Tag> certificateNewTags = certificateTagsChecker(giftCertificateDto.getTags(), giftCertificateDto.getId());
        tagCertificateDao.addToTagCertificateAssociateTable(giftCertificateDto.getId(), certificateNewTags);
        giftCertificateDao.update(giftCertificateDto.getId(), notNullField);
    }

    @Override
    public List<GiftCertificateDto> findByAttributes(String tagName, String searchPart, List<String> fieldsForSort, List<String> orderSort) {
        List<GiftCertificate> giftCertificates = giftCertificateDao.findByCertificateFieldAndSort(tagName, searchPart, fieldsForSort, orderSort);
        return giftCertificates.stream().map(DtoMapper::certificateToDto).collect(Collectors.toList());
    }

    private Map<String, Object> fieldValidator(GiftCertificateDto giftCertificateDto) {
        Map<String, Object> notNullField = new HashMap<>();
        if (giftCertificateDto.getName() != null ) {
            if (!EntityValidator.isNameValid(giftCertificateDto.getName())) {
                throw new EntityFieldValidationException();
            }
            notNullField.put(NAME, giftCertificateDto.getName());
        }
        if (giftCertificateDto.getDescription() != null) {
            if (!EntityValidator.isDescriptionValid(giftCertificateDto.getDescription())) {

                throw new EntityFieldValidationException();
            }
            notNullField.put(DESCRIPTION, giftCertificateDto.getDescription());
        }
        if (giftCertificateDto.getPrice() != null) {
            if (!EntityValidator.isPriceValid(giftCertificateDto.getPrice())) {

                throw new EntityFieldValidationException();
            }
            notNullField.put(PRICE, giftCertificateDto.getPrice());
        }
        if (giftCertificateDto.getDuration() != null) {
            if (!EntityValidator.isDurationValid(giftCertificateDto.getDuration())) {
                throw new EntityFieldValidationException();
            }
            notNullField.put(DURATION, giftCertificateDto.getDuration());
        }
        notNullField.put(LUST_UPDATE_DATE, Timestamp.valueOf(LocalDateTime.now()));
        return notNullField;
    }

    private List<Tag> certificateTagsChecker(List<TagDto> certificateTags, Long certificateId) {
        List<Tag> newCertificateTags = new ArrayList<>();
        if (certificateTags != null && !certificateTags.isEmpty()) {
            certificateTags.forEach(t -> {
                if (t.getName() != null) {
                    Tag tag = tagDao.findOrCreateTag(DtoMapper.dtoToTag(t));
                    if (!tagCertificateDao.checkTagCertificateAssociateTable(certificateId, tag.getId())) {
                        newCertificateTags.add(tag);
                    }
                }
            });
        }
        return newCertificateTags;
    }
}
