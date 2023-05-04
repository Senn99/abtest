package com.ss.abtest.service.impl;

import com.ss.abtest.mapper.UserMapper;
import com.ss.abtest.pojo.RequestResult;
import com.ss.abtest.pojo.domain.User;
import com.ss.abtest.pojo.dto.TableDto;
import com.ss.abtest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public String login(String email, String password) {
        User user = userMapper.getUserByEmail(email);
        if (user != null) {
            if (password.equals(user.getPassword())) {
                return RequestResult.successResult("登录成功",user);
            }else {
                return RequestResult.requestErrorResult("账号密码错误");
            }
        }else {
            User u = new User();
            u.setStatus(0);
            u.setUpdateTime(LocalDateTime.now());
            u.setCreateTime(LocalDateTime.now());
            u.setName("random_name_"+System.currentTimeMillis());
            u.setEmail(email);
            u.setPassword(password);
            u.setSex(0);
            userMapper.addUser(u);
            return RequestResult.successResult("注册成功",u);
        }
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
