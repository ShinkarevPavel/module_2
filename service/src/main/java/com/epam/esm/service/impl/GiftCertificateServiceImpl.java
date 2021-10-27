package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.PageParameterDto;
import com.epam.esm.dto.SearchParameterDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityFieldValidationException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.DtoMapper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        giftCertificateDao.findById(id).orElseThrow(NoSuchEntityException::new);
        giftCertificateDao.delete(id);
    }

    @Transactional
    @Override
    public void update(GiftCertificateDto giftCertificateDto) throws NoSuchEntityException {
        GiftCertificate giftCertificate = giftCertificateDao.findById(giftCertificateDto.getId())
                .orElseThrow(() -> new NoSuchEntityException("error_message.certificate_not_found", String.valueOf(giftCertificateDto.getId())));

        Set<Tag> tags = new HashSet<>();
        if (!CollectionUtils.isEmpty(giftCertificateDto.getTags())) {
            tags = certificateTagsChecker(giftCertificateDto.getTags());
        }
        if (isAllFieldsNull(giftCertificateDto) && tags.isEmpty()) {
            throw new EntityFieldValidationException("certificate.update.no_data_for_update");
        }

        GiftCertificate updatableCertificate = createUpdatableCertificate(giftCertificate, DtoMapper.dtoToCertificate(giftCertificateDto));
        updatableCertificate.getTags().addAll(tags);
        giftCertificateDao.update(updatableCertificate);
    }

    @Override
    public List<GiftCertificateDto> findByAttributes(SearchParameterDto searchParameterDto, PageParameterDto pageParameterDto) {

        return giftCertificateDao.findByCertificateFieldAndSort(DtoMapper.dtoToSearchParameter(searchParameterDto), DtoMapper.dtoToPageParameter(pageParameterDto))
                .stream()
                .map(DtoMapper::certificateToDto)
                .collect(Collectors.toList());
    }

    private GiftCertificate createUpdatableCertificate(GiftCertificate updatableCertificate, GiftCertificate giftCertificate) {
        if (Strings.isNotEmpty(giftCertificate.getName())) {
            updatableCertificate.setName(giftCertificate.getName());
        }

        if (Strings.isNotEmpty(giftCertificate.getDescription())) {
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

    private boolean isAllFieldsNull(GiftCertificateDto giftCertificateDto) {
        return Stream.of(giftCertificateDto.getName(), giftCertificateDto.getDescription(),
                giftCertificateDto.getDuration(), giftCertificateDto.getPrice()).allMatch(Objects::isNull);
    }

    private Set<Tag> certificateTagsChecker(Set<TagDto> certificateTags) {
        Set<Tag> newCertificateTags = new HashSet<>();
        certificateTags.forEach(t -> {
            if (Objects.nonNull(t.getId())) {
                Tag tag = tagDao.findById(t.getId())
                        .orElseThrow(() -> new NoSuchEntityException("giftcertificate.tag.not_present", String.valueOf(t.getId())));

                // This check used for comparing tag name that was found by id with tag name that was passed down
                if (Objects.nonNull(t.getName()) && !t.getName().equalsIgnoreCase(tag.getName())) {
                    throw new EntityFieldValidationException("tag.name.not_correct_id_name_pair", t.getName());
                }
                newCertificateTags.add(tag);
            }
            if (Strings.isNotEmpty(t.getName()) && Objects.isNull(t.getId())) {
                newCertificateTags.add(tagDao.findOrCreateTag(DtoMapper.dtoToTag(t)));
            }
        });
        return newCertificateTags;
    }
}
