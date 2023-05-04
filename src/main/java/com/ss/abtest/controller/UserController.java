package com.ss.abtest.controller;

import com.ss.abtest.pojo.RequestResult;
import com.ss.abtest.pojo.domain.User;
import com.ss.abtest.pojo.dto.TableDto;
import com.ss.abtest.pojo.vo.FlightTable;
import com.ss.abtest.service.UserService;
import com.ss.abtest.util.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
            @RequestParam
                    String email,
            @ApiParam(value = "数据id", required = true, example = "123456")
            @RequestParam
                    String password) {
        if (Strings.isEmpty(email)) {
            return RequestResult.requestErrorResult("用户密码为空");
        }
        if (Strings.isEmpty(password)) {
            return RequestResult.requestErrorResult("用户密码为空");
        }
        return userService.login(email, password);
    }

    @GetMapping("/get")
    public User getUserById(
            @ApiParam(value = "数据id", required = true, example = "1")
                    long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/company/users")
    public String queryCompanyUsers(
            @ApiParam(value = "企业编号", required = true, example = "1")
            @RequestParam(required = true)
                    long companyId,
            @ApiParam(value = "分页编号", required = true, example = "1") int page,
            @ApiParam(value = "分页大小", required = true, example = "1")
                    int limit) {
        TableDto<User> flightTableDto = userService.listCompanyUsers(companyId, page, limit);
        System.out.println(RequestResult.successResult(JsonUtil.parse(flightTableDto)));
        return RequestResult.successResult(flightTableDto);
    }
}
