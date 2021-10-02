package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;

public interface TagDao extends BaseDao<Tag>{
    List<Tag> addCertificateTags(List<Tag> tags);
    Tag findOrCreateTag(Tag tag);
}
