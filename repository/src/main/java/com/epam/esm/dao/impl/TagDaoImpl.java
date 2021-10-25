package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
public class TagDaoImpl implements TagDao {

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
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> query = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = query.from(Tag.class);
        query.select(root);
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public Optional<Tag> findByName(String name) {
        final String NAME_PARAM = "name";
        final int TAG_PARAM = 0;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> query = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = query.from(Tag.class);
        query.select(root);
        query.where(criteriaBuilder.equal(root.get(NAME_PARAM), name));
        List<Tag> tags = entityManager.createQuery(query).getResultList();
        return tags.size() == 0 ? Optional.empty() : Optional.of(tags.get(TAG_PARAM));
        //TOdo What way is better
//        try {
//            return Optional.of(entityManager.createQuery(query).getSingleResult());
//
//        } catch (NoResultException e) {
//            return Optional.empty();
//        }
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

    @Override
    public Optional<Tag> getWidelyUsedTagWithHighestOrderCost() {
        final String MOST_USEFUL_TAG = "SELECT t.id, t.name FROM tags t\n" +
                "INNER JOIN tag_certificate_associate tc ON tc.tag_id = t.id\n" +
                "INNER JOIN gift_certificate gc ON gc.id = tc.gift_id\n" +
                "INNER JOIN order_certificate_associate oc ON oc.certificate_id = gc.id\n" +
                "INNER JOIN orders o ON o.id = oc.order_id \n" +
                "IN (\n" +
                "SELECT tmp.id FROM (\n" +
                "SELECT id, sum(orders.cost) as sumCost\n" +
                "FROM orders\n" +
                "group by id\n" +
                "ORDER BY sumCost DESC\n" +
                ") AS tmp\n" +
                ")\n" +
                "GROUP BY t.id\n" +
                "ORDER BY COUNT(t.id) DESC LIMIT 1";
        Query query = entityManager.createNativeQuery(MOST_USEFUL_TAG, Tag.class);
        List<Tag> tag = query.getResultList();
        return Optional.ofNullable(!tag.isEmpty() ? tag.get(0) : null);
    }
}
