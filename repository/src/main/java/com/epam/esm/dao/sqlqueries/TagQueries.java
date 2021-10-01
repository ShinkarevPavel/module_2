package com.epam.esm.dao.sqlqueries;

public interface TagQueries {
    String GET_BY_NAME = "SELECT t.id, t.name FROM tags AS t WHERE name=?";
    String CREATE_TAG = "INSERT INTO tags (name) VALUES (?)";
}
