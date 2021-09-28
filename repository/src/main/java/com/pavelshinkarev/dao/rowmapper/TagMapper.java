package com.pavelshinkarev.dao.rowmapper;

import com.pavelshinkarev.configuration.SpringDataSourceConfiguration;
import com.pavelshinkarev.entity.Tag;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.pavelshinkarev.dao.TagColumnName.*;

@Component
public class TagMapper implements RowMapper<Tag> {
    @Override
    public Tag mapRow(ResultSet resultSet, int i) throws SQLException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(SpringDataSourceConfiguration.class);
        Tag tag = context.getBean("tag", Tag.class);
        tag.setId(resultSet.getLong(ID));
        tag.setName(resultSet.getString(NAME));
        return tag;
    }
}
