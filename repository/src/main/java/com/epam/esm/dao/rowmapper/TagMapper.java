package com.epam.esm.dao.rowmapper;

import com.epam.esm.entity.Tag;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;


@Component
public class TagMapper implements RowMapper<Tag> {
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";

    @Override
    public Tag mapRow(ResultSet resultSet, int i) throws SQLException {
        Tag tag = new Tag();
        tag.setId(resultSet.getLong(TAG_ID));
        tag.setName(resultSet.getString(TAG_NAME));
        return tag;
    }
}
