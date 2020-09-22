package com.shixi.demo.user.service;

import com.shixi.demo.user.entity.User;
import java.util.List;
import java.util.Map;

public interface UserService {
    List<User> getAll();
    User getUser(String username);
    public void createUser(User user);
}
