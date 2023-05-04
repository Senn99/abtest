package com.ss.abtest.service;

import com.ss.abtest.pojo.domain.User;
import com.ss.abtest.pojo.dto.TableDto;

public interface UserService {
    User getUserById(long id);

    String login(String email, String password);

    TableDto<User> listCompanyUsers(long companyId, int page, int limit);
}
