package com.epam.esm.dao;

import java.util.Optional;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@link BaseDao} is the base interface for {@link GiftCertificate} and {@link TagDao}
 *
 * @param <T>
 */

public interface BaseDao<T> {

    T create(T t);

    Optional<T> findById(long id);

    void delete(long id);

}
