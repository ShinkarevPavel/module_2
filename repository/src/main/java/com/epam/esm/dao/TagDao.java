package com.epam.esm.dao;

import com.epam.esm.entity.Tag;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TagDao extends BaseDao<Tag>{

    List<Tag> addCertificateTags(List<Tag> tags);

    Tag findOrCreateTag(Tag tag);

    List<Tag> findAll(Pageable pageable);

    Optional<Tag> findByName(String name);

    Optional<Tag> getWidelyUsedTagWithHighestOrderCost();


}
