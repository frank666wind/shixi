package com.shixi.demo.user.dao;

import com.shixi.demo.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserDao {
    List<User> getAll();
    User getUser(String username);
    public void createUser(User user);
}

