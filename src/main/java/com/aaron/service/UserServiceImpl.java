package com.aaron.service;

import com.aaron.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    public Boolean has(Long id) {
        return userDao.has(id);
    }
}
