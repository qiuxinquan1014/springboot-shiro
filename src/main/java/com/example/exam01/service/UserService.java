package com.example.exam01.service;

import com.example.exam01.entity.User;

/**
 * User 业务层
 */
public interface UserService {
    User findByName(String username);
}
