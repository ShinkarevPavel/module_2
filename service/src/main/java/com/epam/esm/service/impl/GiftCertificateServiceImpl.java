package com.epam.esm.service.impl;

import com.epam.esm.dao.EntityFields;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.exception.NoSuchEntityFieldException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.DtoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private GiftCertificateDao giftCertificateDao;
    private TagDao tagDao;


    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao, TagDao tagDao) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagDao = tagDao;
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
        GiftCertificate creatableCertificate = DtoMapper.dtoToCertificate(giftCertificateDto);
        creatableCertificate.setTags(certificateTagsChecker(giftCertificateDto.getTags()));
        return DtoMapper.certificateToDto(giftCertificateDao.create(creatableCertificate));
    }

    @Transactional
    @Override
    public void delete(long id) {
        if (giftCertificateDao.findById(id).isEmpty()) {
            throw new NoSuchEntityException();
        }
        giftCertificateDao.delete(id);
    }

    @Transactional
    @Override
    public void update(GiftCertificateDto giftCertificateDto) throws NoSuchEntityException {
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDao.findById(giftCertificateDto.getId());
        if (Objects.isNull(giftCertificateDto.getId()) || optionalGiftCertificate.isEmpty()) {
            throw new NoSuchEntityException();
        }
        GiftCertificate updatableCertificate = createUpdatableCertificate(optionalGiftCertificate.get(), DtoMapper.dtoToCertificate(giftCertificateDto));
        updatableCertificate.setTags(certificateTagsChecker(giftCertificateDto.getTags()));
        giftCertificateDao.update(updatableCertificate);

    }

    @Override
    public List<GiftCertificateDto> findByAttributes(String tagName, String searchPart, List<String> fieldsForSort, List<String> orderSort) {
//        fieldsForSort = sortFieldValidator(fieldsForSort);
//        orderSort = orderSortValidator(orderSort);
//        List<GiftCertificate> giftCertificates = giftCertificateDao.findByCertificateFieldAndSort(tagName, searchPart, fieldsForSort, orderSort);
//        if (!giftCertificates.isEmpty()) {
//            giftCertificates.forEach(certificate -> certificate.setTags(tagCertificateDao.getTagsByCertificateId(certificate.getId())));
//        }
//        return giftCertificates.stream().map(DtoMapper::certificateToDto).collect(Collectors.toList());
        return null;
    }


    private GiftCertificate createUpdatableCertificate(GiftCertificate updatableCertificate, GiftCertificate giftCertificate) {

        boolean isUpdated = false;
        // Todo F`n flag
        if (Objects.nonNull(giftCertificate.getName()) && !giftCertificate.getName().equals(updatableCertificate.getName())) {
            updatableCertificate.setName(giftCertificate.getName());
            isUpdated = true;
        }

        if (Objects.nonNull(giftCertificate.getDescription()) && !giftCertificate.getDescription().equals(updatableCertificate.getDescription())) {
            updatableCertificate.setDescription(giftCertificate.getDescription());
            isUpdated = true;
        }

        if (Objects.nonNull(giftCertificate.getPrice()) && !Objects.equals(giftCertificate.getPrice(), updatableCertificate.getPrice())) {
            updatableCertificate.setPrice(giftCertificate.getPrice());
            isUpdated = true;
        }

        if (Objects.nonNull(giftCertificate.getDuration()) && !Objects.equals(giftCertificate.getDuration(), updatableCertificate.getDuration())) {
            updatableCertificate.setDuration(giftCertificate.getDuration());
            isUpdated = true;
        }

        if (isUpdated) {
            updatableCertificate.setLastUpdateDate(LocalDateTime.now());
        }
        return updatableCertificate;
    }


    private List<Tag> certificateTagsChecker(List<TagDto> certificateTags) {
        List<Tag> newCertificateTags = new ArrayList<>();
        if (Objects.nonNull(certificateTags) && !certificateTags.isEmpty()) {
            certificateTags.forEach(t -> {
                if (Objects.nonNull(t.getName())) {
                    newCertificateTags.add(tagDao.findOrCreateTag(DtoMapper.dtoToTag(t)));
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
