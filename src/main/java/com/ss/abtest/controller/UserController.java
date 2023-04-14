package com.ss.abtest.controller;

import com.ss.abtest.pojo.RequestResult;
import com.ss.abtest.pojo.domain.User;
import com.ss.abtest.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author senn
 * @since 2023/4/2 19:50
 **/
@Api(tags = "用户相关")
@RestController
public class UserController {

    @Autowired
    UserService userService;


    @GetMapping("/login")
    public String login(
            @ApiParam(value = "用户邮箱", required = true, example = "123456@qq.com")
            @RequestParam(required = true)
                    String email,
            @ApiParam(value = "数据id", required = true, example = "123456")
            @RequestParam(required = true)
                    String password) {
        if (Strings.isEmpty(email)) {
            return null;
        }
        if (Strings.isEmpty(password)) {
            return null;
        }
        User user = userService.login(email, password);

        if (!password.equals(user.getPassword())) {
            return RequestResult.requestErrorResult("用户密码错误");
        }
        return RequestResult.successResult(user);
    }

    @GetMapping("/get")
    public User getUserById(
            @ApiParam(value = "数据id", required = true, example = "1")
                    long id) {
        return userService.getUserById(id);
    }
}
