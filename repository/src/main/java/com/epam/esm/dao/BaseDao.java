package com.epam.esm.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import static com.epam.esm.dao.sqlqueries.CertificateSqlQuery.CREATE_PAR_INTO_COMMON_TABLE;

/**
 * {@link BaseDao} is the base interface for {@link GiftCertificate} and {@link TagDao}
 *
 * @param <T>
 */

public interface BaseDao<T> {

    T create(T t);

    Optional<T> findById(long id);

    void delete(long id);

    List<T> findAll();

    Optional<T> findByName(String name);

}
