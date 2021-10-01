package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.rowmapper.TagMapper;
import com.epam.esm.dao.sqlqueries.TagQueries;
import com.epam.esm.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.dao.sqlqueries.CertificateSqlQuery.*;
import static com.epam.esm.dao.sqlqueries.TagQueries.*;

@Builder
@AllArgsConstructor
@Component
public class TagDaoImpl implements TagDao {
    private JdbcTemplate jdbcTemplate;
    private TagMapper tagMapper;

    @Override
    public Tag create(Tag tag) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(CREATE_TAG, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, tag.getName());
            return statement;
        }, keyHolder);
        tag.setId(keyHolder.getKey().longValue());
        return tag;
    }

    @Override
    public Optional<Tag> findById(long id) {
        return Optional.empty();
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public List<Tag> findAll() {
        return null;
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return jdbcTemplate.query(GET_BY_NAME, tagMapper, name).stream().findAny(); // Todo do not use stream
    }

    @Override
    public List<Tag> addCertificateTags(List<Tag> tags) { // Todo rename method
        List<Tag> tagsWithId = new ArrayList<>();
        tags.forEach(t -> {
            if (findByName(t.getName()).isEmpty()) {
                tagsWithId.add(t);
            } else {
                Tag tag = create(t);
                tagsWithId.add(tag);
            }
        });
        return tagsWithId;
    }
}
