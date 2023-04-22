package com.ss.abtest.service.impl;

import com.ss.abtest.mapper.UserMapper;
import com.ss.abtest.pojo.domain.User;
import com.ss.abtest.pojo.dto.TableDto;
import com.ss.abtest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;


    @Override
    public User login(String email, String password) {
        return userMapper.getUserByEmail(email);
    }

    @Override
    public TableDto<User> listCompanyUsers(long companyId, int page, int limit) {
        List<User> list = userMapper.listCompanyUsers(companyId, (page - 1) * limit, limit);
        int total = userMapper.countCompanyUsers(companyId);
        return new TableDto<>(list, total);
    }


    public User getUserById(long id) {
        return userMapper.getUserById(id);
    }


}
