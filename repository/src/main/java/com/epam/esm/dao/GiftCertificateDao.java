package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;
import java.util.Map;

public interface GiftCertificateDao extends BaseDao<GiftCertificate> {

    List<GiftCertificate> findByCertificateFieldAndSort(String tagName, String searchPart, List<String> fieldsForSort, List<String> orderSort);

    void update(GiftCertificate giftCertificate);
}
