package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Component;

import java.util.List;

public interface GiftCertificateDao extends BaseDao<GiftCertificate>{
    public void addToAssociateTable(long id, List<Tag> tags);
}
