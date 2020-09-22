package com.shixi.demo.user.service;

import com.shixi.demo.user.dao.UserDao;
import com.shixi.demo.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;
    @Override
    public List<User> getAll(){
        return userDao.getAll();
    }
    @Override
    public User getUser(String username){
        return userDao.getUser(username);
    }
    @Override
    public void createUser(User user){
        userDao.createUser(user);
    }
}
