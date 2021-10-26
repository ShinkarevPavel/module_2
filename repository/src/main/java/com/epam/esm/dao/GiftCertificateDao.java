package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.data.domain.Pageable;

import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;

public interface GiftCertificateDao extends BaseDao<GiftCertificate> {

    List<GiftCertificate> findByCertificateFieldAndSort(List<String> tagName, String searchPart, List<String> fieldsForSort, List<String> orderSort, Pageable pageable);

    void update(GiftCertificate giftCertificate);

    List<Tag> getCertificateTags(Long giftCertificateId);
}
