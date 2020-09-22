package com.shixi.demo.user.controller;

import com.shixi.demo.user.entity.User;
import com.shixi.demo.user.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/getAll")
    public List<User> getAll(){
        return userService.getAll();
    }
    @RequestMapping("/getUser")
    public User getUser(String username){
        return userService.getUser(username);
    }
    @RequestMapping("/createUser")
    @ResponseBody
    public void createUser(@RequestBody User user){
        userService.createUser(user);
    }
}
