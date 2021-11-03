package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.PageParameter;
import com.epam.esm.entity.SearchParameter;
import com.epam.esm.entity.Tag;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.*;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    @PersistenceContext
    private EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;

    public GiftCertificateDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    @Override
    public GiftCertificate create(GiftCertificate certificate) {
        entityManager.persist(certificate);
        return certificate;
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        return Optional.ofNullable(entityManager.find(GiftCertificate.class, id));
    }

    @Override
    public void delete(long id) {
        entityManager.remove(entityManager.find(GiftCertificate.class, id));
    }

    @Override
    public void update(GiftCertificate giftCertificate) {
        entityManager.merge(giftCertificate);
    }

    @Override
    public Set<Tag> getCertificateTags(Long giftCertificateId) {
        CriteriaQuery<Tag> query = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = query.from(Tag.class);
        Join<Tag, GiftCertificate> join = root.join("giftCertificates");
        query.select(root);
        query.where(criteriaBuilder.equal(join.get("id"), giftCertificateId));
        Set<Tag> tagSet = new HashSet<>(entityManager.createQuery(query).getResultList());
        return tagSet;
    }

    @Override
    public List<GiftCertificate> findByCertificateFieldAndSort(SearchParameter searchParameter, PageParameter pageParameter) {
        CriteriaQuery<GiftCertificate> query = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = query.from(GiftCertificate.class);
        query.select(root);

        List<Predicate> predicates = new ArrayList<>();
        if (!CollectionUtils.isEmpty(searchParameter.getTagName())) {
            predicates.add(buildPredicateByTagName(root, searchParameter.getTagName()));
        }

        if (Strings.isNotEmpty(searchParameter.getSearchPart())) {
            predicates.add(getPartSearchPredicate(root, searchParameter.getSearchPart()));
        }

        if (!predicates.isEmpty()) {
            Predicate resultPredicate = predicates.get(0);
            for (int i = 1; i < predicates.size(); i++) {
                resultPredicate = criteriaBuilder.and(resultPredicate, predicates.get(i));
            }
            query.where(resultPredicate);
            if (!CollectionUtils.isEmpty(searchParameter.getTagName())) {
                query.groupBy(root.get("id"));
                query.having(criteriaBuilder.greaterThanOrEqualTo(criteriaBuilder.count(root),
                        (long) searchParameter.getTagName().size()));
            }
        }

        if (!CollectionUtils.isEmpty(searchParameter.getFieldsForSort())) {
            List<Order> orderList = buildSortedOrderList(searchParameter.getFieldsForSort(), searchParameter.getOrderSort(), root);
            if (!orderList.isEmpty()) {
                query.orderBy(orderList);
            }
        }
        List<GiftCertificate> listCertificate = entityManager.createQuery(query)
                .setFirstResult((pageParameter.getPage()) * pageParameter.getSize())
                .setMaxResults(pageParameter.getSize())
                .getResultList();
        return listCertificate;
    }

    private List<Order> buildSortedOrderList(List<String> fieldsForSort, List<String> orderSort, Root<GiftCertificate> root) {
        List<Order> orderList = new ArrayList<>();
        String orderType;
        for (int i = 0; i < fieldsForSort.size(); i++) {
            if (Objects.nonNull(orderSort) && i < orderSort.size()) {
                orderType = orderSort.get(i);
            } else {
                orderType = "asc";
            }
            Order order;
            if (orderType.equalsIgnoreCase("asc")) {
                order = criteriaBuilder.asc(root.get(fieldsForSort.get(i)));
            } else {
                order = criteriaBuilder.desc(root.get(fieldsForSort.get(i)));
            }
            orderList.add(order);
        }
        return orderList;
    }

    private Predicate getPartSearchPredicate(Root<GiftCertificate> root, String partSearch) {
        Predicate namePredicate = criteriaBuilder.like(root.get("name"), "%" + partSearch + "%");
        Predicate descriptionPredicate = criteriaBuilder.like(root.get("description"), "%" + partSearch + "%");
        return criteriaBuilder.or(namePredicate, descriptionPredicate);
    }

    private Predicate buildPredicateByTagName(Root<GiftCertificate> root, List<String> tagNames) {
        Join<GiftCertificate, Tag> tagsJoin = root.join("tags");
        int counter = 0;
        Predicate predicate = null;
        for (String t : tagNames) {
            Predicate currentPredicate = criteriaBuilder.equal(tagsJoin.get("name"), t);
            if (counter++ == 0) {
                predicate = currentPredicate;
            } else {
                predicate = criteriaBuilder.or(predicate, currentPredicate);
            }
        }
        return predicate;
    }
}
