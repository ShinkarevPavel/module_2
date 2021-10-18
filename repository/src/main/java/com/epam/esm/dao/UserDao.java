package com.epam.esm.dao;

import com.epam.esm.entity.User;

public interface UserDao extends BaseDao<User> {

    User update(User user);

    public boolean isContains(long id);
}
