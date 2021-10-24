package com.epam.esm.service.impl;

import com.epam.esm.dao.EntityFields;
import com.epam.esm.dao.GiftCertificateDao;
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
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl<T> implements GiftCertificateService {

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
        //TODO check all certificate fields on null or Emptiness
        if (isAllFieldsNull(giftCertificateDto)) {

        }
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDao.findById(giftCertificateDto.getId());
        if (optionalGiftCertificate.isEmpty()) {
            throw new NoSuchEntityException("error_message.certificate_not_found", String.valueOf(giftCertificateDto.getId()));
        }
        GiftCertificate updatableCertificate = createUpdatableCertificate(optionalGiftCertificate.get(), DtoMapper.dtoToCertificate(giftCertificateDto));

        List<Tag> tags;
        if (isNotEmpty(giftCertificateDto.getTags())) {
            tags = certificateTagsChecker(giftCertificateDto.getTags());
            if (tags.isEmpty()) {
                tags = giftCertificateDao.getCertificateTags(giftCertificateDto.getId());
            }
        } else {
            tags = giftCertificateDao.getCertificateTags(giftCertificateDto.getId());
        }
        updatableCertificate.setTags(tags);
        giftCertificateDao.update(updatableCertificate);

    }

    @Override
    public List<GiftCertificateDto> findByAttributes(List<String> tagName, String searchPart, List<String> fieldsForSort, List<String> orderSort) {
        return giftCertificateDao.findByCertificateFieldAndSort(tagName, searchPart, fieldsForSort, orderSort)
                .stream()
                .map(DtoMapper::certificateToDto)
                .collect(Collectors.toList());
    }


    private GiftCertificate createUpdatableCertificate(GiftCertificate updatableCertificate, GiftCertificate giftCertificate) {
        if (Objects.nonNull(giftCertificate.getName())) {
            updatableCertificate.setName(giftCertificate.getName());
        }

        if (Objects.nonNull(giftCertificate.getDescription())) {
            updatableCertificate.setDescription(giftCertificate.getDescription());
        }

        if (Objects.nonNull(giftCertificate.getPrice())) {
            updatableCertificate.setPrice(giftCertificate.getPrice());
        }

        if (Objects.nonNull(giftCertificate.getDuration())) {
            updatableCertificate.setDuration(giftCertificate.getDuration());
        }
        return updatableCertificate;
    }


// TODO !
    private boolean isAllFieldsNull(GiftCertificateDto giftCertificateDto) throws IllegalAccessException {
        Field[] fields = GiftCertificateDto.class.getDeclaredFields();
        for (Field f : fields) {
            if (f.get(this) instanceof String) {
                if (Strings.isNotEmpty((String)f.get(this))) {
                    return false;
                }
            }
            if (f.get(this) instanceof Number) {
                if (Objects.nonNull(f.get(this))) {
                    return false;
                }
            }
            if (f.get(this) instanceof Collection) {
                if (Objects.nonNull(f.get(this))) {
                    return false;
                }
            }
        }
        return true;
    }


    private List<Tag> certificateTagsChecker(List<TagDto> certificateTags) {
        Set<Tag> newCertificateTags = new HashSet<>();
        certificateTags.forEach(t -> {
            if (Objects.nonNull(t.getId())) {
                Optional<Tag> tagOptional = tagDao.findById(t.getId());
                if (tagOptional.isEmpty()) {
                    throw new NoSuchEntityException("giftcertificate.tag.not_present", String.valueOf(t.getId()));
                }
                // This check used for comparing tag name that was found by id with tag name that was passed down
                if (Objects.nonNull(t.getName()) && !t.getName().equalsIgnoreCase(tagOptional.get().getName())) {
                    throw new EntityFieldValidationException("tag.name.not_correct_id_name_pair", t.getName());
                }
                    newCertificateTags.add(tagOptional.get());
            }
            if (Strings.isNotEmpty(t.getName()) && Objects.isNull(t.getId())) {
                newCertificateTags.add(tagDao.findOrCreateTag(DtoMapper.dtoToTag(t)));
            }
        });
        return newCertificateTags.stream().toList();
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

    private boolean isNotEmpty(List<?> values) {
        if (Objects.nonNull(values) && !values.isEmpty()) {
            return true;
        }
        return false;
    }

}
