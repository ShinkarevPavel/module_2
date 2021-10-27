package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.PageParameter;
import com.epam.esm.entity.SearchParameter;
import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Set;

public interface GiftCertificateDao extends BaseDao<GiftCertificate> {

    List<GiftCertificate> findByCertificateFieldAndSort(SearchParameter searchParameter, PageParameter pageParameter);

    void update(GiftCertificate giftCertificate);

    Set<Tag> getCertificateTags(Long giftCertificateId);
}
