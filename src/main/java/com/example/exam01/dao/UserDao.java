package com.example.exam01.dao;

import com.example.exam01.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * User 持久层
 */
@Repository
@Mapper
public interface UserDao {

    @Select("SELECT * FROM user WHERE username = #{username}")
    User findByName(@Param("username") String username);
}
