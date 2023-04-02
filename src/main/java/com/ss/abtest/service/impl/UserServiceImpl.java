package com.ss.abtest.service.impl;

import com.ss.abtest.mapper.UserMapper;
import com.ss.abtest.pojo.domain.User;
import com.ss.abtest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    public User getUserById(long id) {
        return userMapper.getUserById(id);
    }

}
