package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;

public interface GiftCertificateDao extends BaseDao<GiftCertificate> {

    List<GiftCertificate> findByCertificateFieldAndSort(List<String> tagName, String searchPart, List<String> fieldsForSort, List<String> orderSort);

    void update(GiftCertificate giftCertificate);

}
