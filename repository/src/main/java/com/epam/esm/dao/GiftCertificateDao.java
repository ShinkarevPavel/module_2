package com.epam.esm.dao;

import com.epam.esm.entity.Tag;
import com.epam.esm.entity.GiftCertificate;

import java.util.List;
import java.util.Map;

public interface GiftCertificateDao extends BaseDao<GiftCertificate>{
    void addToAssociateTable(long id, List<Tag> tags);

    void deleteFromAssociateTable(long id);

    boolean isPresent(long id);

    GiftCertificate update(Map<String, Object> notNullFields, long id);

    boolean checkAssociateTable(Long certificateId, Long tagId);
}
