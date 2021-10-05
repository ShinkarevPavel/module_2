package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.SqlQueryBuilder;
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

    @Override
    public List<GiftCertificateDto> getAll() {
        List<GiftCertificate> giftCertificateList = giftCertificateDao.findAll();
        return giftCertificateList.stream()
                .map(DtoMapper::certificateToDto)
                .collect(Collectors.toList());
    }

    @Override
    public GiftCertificateDto getById(long id) throws NoSuchEntityException{
        if (!giftCertificateDao.isPresent(id)) {
            throw new NoSuchEntityException("Certificate with id=" + id + " is not found");
        }
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
        giftCertificateDao.addToAssociateTable(certificate.getId(), certificate.getTags());
        return DtoMapper.certificateToDto(certificate);
    }

    @Transactional
    @Override
    public void delete(long id) {
        giftCertificateDao.deleteFromAssociateTable(id);
        giftCertificateDao.delete(id);
    }

    @Transactional
    @Override
    public void update(GiftCertificateDto giftCertificateDto) {
        if (!giftCertificateDao.isPresent(giftCertificateDto.getId()) || giftCertificateDto.getId() == null) {
            throw new NoSuchEntityException("There is no for update certificate with id=" + giftCertificateDto.getId());
        }
        Map<String, Object> notNullField = fieldCheck(giftCertificateDto);
        List<Tag> certificateNewTags = certificateTagsChecker(giftCertificateDto.getTags(), giftCertificateDto.getId());
        giftCertificateDao.addToAssociateTable(giftCertificateDto.getId(), certificateNewTags);
        giftCertificateDao.update(giftCertificateDto.getId(), notNullField);
    }

    @Override
    public List<GiftCertificateDto> findByAttributes(String tagName, String searchPart, List<String> fieldsForSort, List<String> orderSort) {
        List<GiftCertificate> giftCertificates = giftCertificateDao.findByNameOrDescription(tagName, searchPart, fieldsForSort, orderSort);
        return giftCertificates.stream().map(DtoMapper::certificateToDto).collect(Collectors.toList());
    }

    private Map<String, Object> fieldCheck(GiftCertificateDto giftCertificateDto) {
        Map<String, Object> notNullField = new HashMap<>();
        if (giftCertificateDto.getName() != null ) {
            if (!EntityValidator.isNameValid(giftCertificateDto.getName())) {
                throw new EntityFieldValidationException("Value '" + giftCertificateDto.getName() + "' for name field is not valid");
            }
            notNullField.put(NAME, giftCertificateDto.getName());
        }
        if (giftCertificateDto.getDescription() != null) {
            if (!EntityValidator.isDescriptionValid(giftCertificateDto.getDescription())) {
                throw new EntityFieldValidationException("Value '" + giftCertificateDto.getDescription() + "' for description field is not valid");
            }
            notNullField.put(DESCRIPTION, giftCertificateDto.getDescription());
        }
        if (giftCertificateDto.getPrice() != null) {
            if (!EntityValidator.isPriceValid(giftCertificateDto.getPrice())) {
                throw new EntityFieldValidationException("Value '" + giftCertificateDto.getPrice() + "' for price field is not valid");
            }
            notNullField.put(PRICE, giftCertificateDto.getPrice());
        }
        if (giftCertificateDto.getDuration() != null) {
            if (!EntityValidator.isDurationValid(giftCertificateDto.getDuration())) {
                throw new EntityFieldValidationException("Value '" + giftCertificateDto.getDuration() + "' for duration field is not valid");
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
//                    Todo ? Optional in this place.
                    Tag tag = tagDao.findOrCreateTag(DtoMapper.dtoToTag(t));
                    if (!giftCertificateDao.checkAssociateTable(certificateId, tag.getId())) {
                        newCertificateTags.add(tag);
                    }
                }
            });
        }
        return newCertificateTags;
    }
}
