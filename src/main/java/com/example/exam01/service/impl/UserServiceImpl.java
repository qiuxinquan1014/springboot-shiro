package com.example.exam01.service.impl;

import com.example.exam01.dao.UserDao;
import com.example.exam01.entity.User;
import com.example.exam01.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * User 业务层实现类
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public User findByName(String username) {
        return userDao.findByName(username);
    }
}
