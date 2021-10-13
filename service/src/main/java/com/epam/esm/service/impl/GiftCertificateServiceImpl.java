package com.epam.esm.service.impl;

import com.epam.esm.dao.EntityFields;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityFieldValidationException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.exception.NoSuchEntityFieldException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.DtoMapper;
import com.epam.esm.validator.EntityValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.epam.esm.dao.EntityFields.*;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private GiftCertificateDao giftCertificateDao;
    private TagDao tagDao;
    private TagCertificateDao tagCertificateDao;

    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao, TagDao tagDao, TagCertificateDao tagCertificateDao) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagDao = tagDao;
        this.tagCertificateDao = tagCertificateDao;
    }

    @Override
    public GiftCertificateDto getById(long id) throws NoSuchEntityException {
        return giftCertificateDao.findById(id)
                .map(DtoMapper::certificateToDto)
                .orElseThrow(NoSuchEntityException::new);
    }


    @Transactional
    @Override
    public GiftCertificateDto create(GiftCertificateDto giftCertificateDto) {
        GiftCertificate certificate = null;
        if (EntityValidator.isValidEntityFieldForCreate(giftCertificateDto)) {
            certificate = DtoMapper.dtoToCertificate(giftCertificateDto);
            certificate = giftCertificateDao.create(certificate);
            if (certificate.getTags() != null) {
                certificate.setTags(tagDao.addCertificateTags(certificate.getTags()));
                tagCertificateDao.addToTagCertificateAssociateTable(certificate.getId(), certificate.getTags());
            }
        }
        return DtoMapper.certificateToDto(certificate);
    }

    @Transactional
    @Override
    public void delete(long id) {
        if (giftCertificateDao.findById(id).isEmpty()) {
            throw new NoSuchEntityException();
        }
        tagCertificateDao.deleteFromTagCertificateAssociateTable(id);
        giftCertificateDao.delete(id);
    }

    @Transactional
    @Override
    public void update(GiftCertificateDto giftCertificateDto) throws NoSuchEntityException {
        if (giftCertificateDto.getId() == null || giftCertificateDao.findById(giftCertificateDto.getId()).isEmpty()) {
            throw new NoSuchEntityException();
        }
        Map<String, Object> notNullField = fieldValidator(giftCertificateDto);
        List<Tag> certificateNewTags = certificateTagsChecker(giftCertificateDto.getTags(), giftCertificateDto.getId());
        tagCertificateDao.addToTagCertificateAssociateTable(giftCertificateDto.getId(), certificateNewTags);
        giftCertificateDao.update(giftCertificateDto.getId(), notNullField);
    }

    @Override
    public List<GiftCertificateDto> findByAttributes(String tagName, String searchPart, List<String> fieldsForSort, List<String> orderSort) {
        fieldsForSort = sortFieldValidator(fieldsForSort);
        orderSort = orderSortValidator(orderSort);
        List<GiftCertificate> giftCertificates = giftCertificateDao.findByCertificateFieldAndSort(tagName, searchPart, fieldsForSort, orderSort);
        if (!giftCertificates.isEmpty()) {
            giftCertificates.forEach(certificate -> certificate.setTags(tagCertificateDao.getTagsByCertificateId(certificate.getId())));
        }
        return giftCertificates.stream().map(DtoMapper::certificateToDto).collect(Collectors.toList());
    }


    private Map<String, Object> fieldValidator(GiftCertificateDto giftCertificateDto) {
        Map<String, Object> notNullField = new HashMap<>();
        if (giftCertificateDto.getName() != null) {
            if (!EntityValidator.isNameValid(giftCertificateDto.getName())) {
                throw new EntityFieldValidationException("error_message.name");
            }
            notNullField.put(NAME.getName(), giftCertificateDto.getName());
        }
        if (giftCertificateDto.getDescription() != null) {
            if (!EntityValidator.isDescriptionValid(giftCertificateDto.getDescription())) {
                throw new EntityFieldValidationException("error_message.description");
            }
            notNullField.put(DESCRIPTION.getName(), giftCertificateDto.getDescription());
        }
        if (giftCertificateDto.getPrice() != null) {
            if (!EntityValidator.isPriceValid(giftCertificateDto.getPrice())) {
                throw new EntityFieldValidationException("error_message.price");
            }
            notNullField.put(PRICE.getName(), giftCertificateDto.getPrice());
        }
        if (giftCertificateDto.getDuration() != null) {
            if (!EntityValidator.isDurationValid(giftCertificateDto.getDuration())) {
                throw new EntityFieldValidationException("error_message.duration");
            }
            notNullField.put(DURATION.getName(), giftCertificateDto.getDuration());
        }
        notNullField.put(LAST_UPDATE_DATE.getName(), Timestamp.valueOf(LocalDateTime.now()));
        return notNullField;
    }



    private List<Tag> certificateTagsChecker(List<TagDto> certificateTags, Long certificateId) {
        List<Tag> newCertificateTags = new ArrayList<>();
        if (certificateTags != null && !certificateTags.isEmpty()) {
            certificateTags.forEach(t -> {
                if (t.getName() != null) {
                    Tag tag = tagDao.findOrCreateTag(DtoMapper.dtoToTag(t));
                    if (!tagCertificateDao.isPresentTagAndCertificateInAssociateTable(certificateId, tag.getId())) {
                        newCertificateTags.add(tag);
                    }
                }
            });
        }
        return newCertificateTags;
    }

    private List<String> sortFieldValidator(List<String> fieldsForSort) {
        if (fieldsForSort != null) {
            fieldsForSort = fieldsForSort.stream().map(String::toLowerCase).collect(Collectors.toList());
            List<String> fields = EntityFields.getFields();
            if (!fields.containsAll(fieldsForSort)) {
                throw new NoSuchEntityFieldException();
            }
        }
        return fieldsForSort;
    }

    private List<String> orderSortValidator(List<String> orderSort) {
        if (orderSort != null) {
            orderSort = orderSort.stream().map(String::toLowerCase).collect(Collectors.toList());
            orderSort.forEach(o -> {
                if (!o.equals("desc") && !o.equals("asc")) {
                    throw new NoSuchEntityFieldException();
                }
            });
        }
        return orderSort;
    }

}
