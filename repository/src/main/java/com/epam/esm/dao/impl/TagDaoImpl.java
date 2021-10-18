package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
public class TagDaoImpl implements TagDao {
    private static final String NAME_PARAM = "name";
    private static final String GET_BY_NAME = "SELECT t FROM Tag t WHERE t.name= :name";
    private static final String FIND_ALL = "SELECT t FROM Tag t";


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Tag create(Tag tag) {
        entityManager.persist(tag);
        return tag;
    }

    @Override
    public Optional<Tag> findById(long id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }

    @Override
    public void delete(long id) {
        entityManager.remove(entityManager.find(Tag.class, id));
    }

    @Override
    public List<Tag> findAll() {
        return entityManager.createQuery(FIND_ALL, Tag.class).getResultList();
    }

    @Override
    public Optional<Tag> findByName(String name) {
        Tag tag = null;
        Query query = entityManager.createQuery(GET_BY_NAME);
        int result = query.setParameter(NAME_PARAM, name).getFirstResult();
        if (result != 0) {
            tag = (Tag) query.setParameter(NAME_PARAM, name).getSingleResult(); // Todo use try/catch and only getSingleResult
        }
        return Optional.ofNullable(tag);
    }

    @Override
    public Tag findOrCreateTag(Tag tag) {
        return findByName(tag.getName()).orElseGet(() -> create(tag));
    }

    @Override
    public List<Tag> addCertificateTags(List<Tag> tags) {
        List<Tag> tagsWithId = new ArrayList<>();
        tags.forEach(t -> tagsWithId.add(findOrCreateTag(t)));
        return tagsWithId;
    }
}
