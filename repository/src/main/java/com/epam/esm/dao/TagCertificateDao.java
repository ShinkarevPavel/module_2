package com.epam.esm.dao;

import com.epam.esm.entity.Tag;
import lombok.Builder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;

@Component
@Builder
public class TagCertificateDao {
    private static final String CREATE_PAR_INTO_COMMON_TABLE = "INSERT INTO tag_certificate_associate (gift_id, tag_id) VALUES (?, ?)";
    private static final String FIND_PAR_INTO_COMMON_TABLE = "SELECT count(*) FROM tag_certificate_associate WHERE gift_id = ? and tag_id=?";
    private static final String DELETE_BY_ID_FROM_COMMON_TABLE = "DELETE FROM tag_certificate_associate WHERE id=?";

    private JdbcTemplate jdbcTemplate;

    public void deleteFromTagCertificateAssociateTable(long id) {
        jdbcTemplate.update(DELETE_BY_ID_FROM_COMMON_TABLE, id);
    }


    public boolean checkTagCertificateAssociateTable(Long certificateId, Long tagId) {
        return jdbcTemplate.queryForObject(FIND_PAR_INTO_COMMON_TABLE, Integer.class, certificateId, tagId) > 0;
    }


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
}
