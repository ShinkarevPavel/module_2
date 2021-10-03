package com.epam.esm.util;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.SqlQueryBuilder;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import lombok.*;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Component
@Builder
@AllArgsConstructor
public class CertificateFieldOnNullChecker {

    private GiftCertificateDao giftCertificateDao;
    private TagDao tagDao;

    public Map<String, Object> fieldCheck(GiftCertificateDto giftCertificateDto) {
        Map<String, Object> notNullField = new HashMap<>();
        SqlQueryBuilder queryBuilder = new SqlQueryBuilder();
        if (!giftCertificateDao.isPresent(giftCertificateDto.getId()) || giftCertificateDto.getId() == null) {
//            throw new EXC
        }
        if (giftCertificateDto.getName() != null) {
            notNullField.put(queryBuilder.getNAME(), giftCertificateDto.getName());
        }
        if (giftCertificateDto.getDescription() != null) {
            notNullField.put(queryBuilder.getDESCRIPTION(), giftCertificateDto.getDescription());
        }
        if (giftCertificateDto.getPrice() != null) {
            notNullField.put(queryBuilder.getPRICE(), giftCertificateDto.getPrice());
        }
        if (giftCertificateDto.getDuration() != null) {
            notNullField.put(queryBuilder.getDURATION(), giftCertificateDto.getDuration());
        }
        notNullField.put(queryBuilder.getLAST_UPDATE_DATE(), Timestamp.valueOf(LocalDateTime.now()));
        return notNullField;
    }


    public List<Tag> certificateTagsChecker(List<TagDto> certificateTags, Long certificateId) {
        List<Tag> newCertificateTags = new ArrayList<>();
        if (certificateTags != null && !certificateTags.isEmpty()) {
            certificateTags.forEach(t -> {
                if (t.getName() != null) {
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
