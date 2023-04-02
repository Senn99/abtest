package com.ss.abtest.controller;

import com.ss.abtest.pojo.domain.User;
import com.ss.abtest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/get")
    public User getUserById(long id) {
        return userService.getUserById(id);
    }
}
