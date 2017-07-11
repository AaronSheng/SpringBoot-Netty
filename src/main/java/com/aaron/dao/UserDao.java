package com.aaron.dao;

import com.aaron.dao.base.GenericDao;
import com.aaron.entity.User;

public interface UserDao extends GenericDao<User, Long> {
    Boolean has(Long id);
}
