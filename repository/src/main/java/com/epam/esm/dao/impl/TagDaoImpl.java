package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.dao.rowmapper.TagMapper;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Builder
@RequiredArgsConstructor
@Component
public class TagDaoImpl implements TagDao {
    private static final String GET_BY_NAME = "SELECT t.id, t.name FROM tags AS t WHERE name=?";
    private static final String CREATE_TAG = "INSERT INTO tags (name) VALUES (?)";
    private static final String FIND_BY_ID = "SELECT id, name FROM tags WHERE id=?";
    private static final String FIND_ALL = "SELECT * FROM tags";

    @NonNull
    private JdbcTemplate jdbcTemplate;
    @NonNull
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
        return jdbcTemplate.query(FIND_BY_ID, tagMapper, id).stream().findAny();
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException("Operation is not supported");
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(FIND_ALL, tagMapper);
    }

    @Override
    public Optional<Tag> findByName(String name) {
        Optional<Tag> optionalTag = Optional.empty();
        List<Tag> tags = jdbcTemplate.query(GET_BY_NAME, tagMapper, name);
        return tags.isEmpty() ? optionalTag : Optional.of(tags.get(0));
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
