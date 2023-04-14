package com.ss.abtest.service.impl;

import com.ss.abtest.mapper.UserMapper;
import com.ss.abtest.pojo.domain.User;
import com.ss.abtest.service.UserService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;


    @Override
    public User login(String email, String password) {
        return  userMapper.getUserByEmail(email);
    }


    public User getUserById(long id) {
        return userMapper.getUserById(id);
    }


}
