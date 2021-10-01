package com.epam.esm.dao.sqlqueries;

public interface CertificateSqlQuery { // Todo for what ??? Tranfer to Dao
    String SELECT_ALL_GIFT_CERTIFICATES = "SELECT gc.id, gc.name, gc.description, gc.price, gc.duration, gc.create_date, " +
            "gc.last_update_date,t.id, t.name FROM gift_certificate AS gc LEFT JOIN associativetable " +
            "AS at ON gc.id=at.gift_id LEFT JOIN tags AS t ON at.tag_id=t.id";

    String CREATE_CERTIFICATE = "INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date) VALUES (?, ?, ?, ?, ?, ?)";
    String CREATE_PAR_INTO_COMMON_TABLE = "INSERT INTO associativetable (gift_id, tag_id) VALUES (?, ?)";
    String SELECT_BY_ID = SELECT_ALL_GIFT_CERTIFICATES + " WHERE gc.id=?";
    String DELETE_BY_ID = "DELETE FROM gift_certificate WHERE id=?";
    String DELETE_BY_ID_FROM_COMMON_TABLE = "DELETE FROM associativetable WHERE id=?";
}
