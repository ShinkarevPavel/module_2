package com.pavelshinkarev.dao;

import java.util.List;
import java.util.Optional;

import com.pavelshinkarev.entity.GiftCertificate;

/**
 * {@link BaseDao} is the base interface for {@link GiftCertificate} and {@link TagDao}
 *
 * @param <T>
 */

public interface BaseDao<T> {

    void create(T t);

    Optional<T> findById(long id);

    void delete(long id);

    List<T> findAll();

}
