package com.epam.esm.dao.rowmapper;

import com.epam.esm.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.esm.dao.TagColumnName.*;

@Component
@Builder
public class TagMapper implements RowMapper<Tag> {

    @Override
    public Tag mapRow(ResultSet resultSet, int i) throws SQLException {
        Tag tag = new Tag();
        tag.setId(resultSet.getLong(TAG_ID));
        tag.setName(resultSet.getString(TAG_NAME));
        return tag;
    }
}
