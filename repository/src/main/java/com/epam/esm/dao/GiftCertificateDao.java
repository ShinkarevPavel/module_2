package com.epam.esm.dao;

import com.epam.esm.entity.Tag;
import com.epam.esm.entity.GiftCertificate;

import java.util.List;
import java.util.Map;

public interface GiftCertificateDao extends BaseDao<GiftCertificate>{

    void update(long id, Map<String, Object> notNullFields);

    List<GiftCertificate> findByCertificateFieldAndSort(String tagName, String searchPart, List<String> fieldsForSort, List<String> orderSort);
}
