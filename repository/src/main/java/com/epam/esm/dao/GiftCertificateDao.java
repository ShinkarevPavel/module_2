package com.epam.esm.dao;

import com.epam.esm.entity.Tag;
import com.epam.esm.entity.GiftCertificate;

import java.util.List;

public interface GiftCertificateDao extends BaseDao<GiftCertificate>{
    void addToAssociateTable(long id, List<Tag> tags);
    void deleteFromAssociateTable(long id);
}
