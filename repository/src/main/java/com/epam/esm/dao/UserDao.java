package com.epam.esm.dao;

import com.epam.esm.entity.PageParameter;
import com.epam.esm.entity.User;

import java.util.List;

public interface UserDao extends BaseDao<User> {

    User update(User user);

    boolean isContains(long id);

    List<User> getAll(PageParameter pageParameter);
}
