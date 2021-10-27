package com.epam.esm.dao;

import com.epam.esm.entity.PageParameter;
import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDao extends BaseDao<Tag>{

    List<Tag> addCertificateTags(List<Tag> tags);

    Tag findOrCreateTag(Tag tag);

    List<Tag> findAll(PageParameter pageParameter);

    Optional<Tag> findByName(String name);

    Optional<Tag> getWidelyUsedTagWithHighestOrderCost();


}
