package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;

public interface TagCertificateDao {
    boolean isPresentTagAndCertificateInAssociateTable(Long certificateId, Long tagId);

    void addToTagCertificateAssociateTable(long id, List<Tag> tags);

    boolean isPresentRowByTagId(Long id);

    void deleteFromTagCertificateAssociateTable(long id);

    List<Tag> getTagsByCertificateId(Long id);
}
