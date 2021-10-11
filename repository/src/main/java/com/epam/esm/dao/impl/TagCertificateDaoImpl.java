package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagCertificateDao;
import com.epam.esm.dao.rowmapper.TagMapper;
import com.epam.esm.entity.Tag;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;

@Component
public class TagCertificateDaoImpl implements TagCertificateDao {
    private static final String CREATE_PAR_INTO_COMMON_TABLE = "INSERT INTO tag_certificate_associate (gift_id, tag_id) VALUES (?, ?)";
    private static final String DELETE_BY_ID_FROM_COMMON_TABLE = "DELETE FROM tag_certificate_associate WHERE gift_id=?";
    private static final String FIND_BY_TAG_ID_FROM_COMMON_TABLE = "SELECT count(*) FROM tag_certificate_associate WHERE tag_id=?";
    private static final String FIND_TAGS_BY_CERTIFICATE_ID = "SELECT tag_id, t.id as t_id, t.name as t_name FROM tag_certificate_associate as tca LEFT JOIN tags as t ON tag_id = t.id WHERE gift_id = ?";

    private JdbcTemplate jdbcTemplate;
    private TagMapper tagMapper;


    public TagCertificateDaoImpl(JdbcTemplate jdbcTemplate, TagMapper tagMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagMapper = tagMapper;
    }

    @Override
    public void deleteFromTagCertificateAssociateTable(long id) {
        jdbcTemplate.update(DELETE_BY_ID_FROM_COMMON_TABLE, id);
    }


    @Override
    public boolean isPresentTagAndCertificateInAssociateTable(Long certificateId, Long tagId) {
        return jdbcTemplate.queryForObject(FIND_BY_TAG_ID_FROM_COMMON_TABLE, Integer.class, certificateId, tagId) > 0;
    }

    @Override
    public void addToTagCertificateAssociateTable(long id, List<Tag> tags) {
        for (Tag tag : tags) {
            jdbcTemplate.update(con -> {
                PreparedStatement statement = con.prepareStatement(CREATE_PAR_INTO_COMMON_TABLE);
                statement.setLong(1, id);
                statement.setLong(2, tag.getId());
                return statement;
            });
        }
    }

    @Override
    public boolean isPresentRowByTagId(Long id) {
        return jdbcTemplate.queryForObject(FIND_BY_TAG_ID_FROM_COMMON_TABLE, Integer.class, id) > 0;
    }

    @Override
    public List<Tag> getTagsByCertificateId(Long id) {
        return jdbcTemplate.query(FIND_TAGS_BY_CERTIFICATE_ID,tagMapper, id);
    }
}
