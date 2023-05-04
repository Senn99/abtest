package com.ss.abtest.mapper;


import com.ss.abtest.pojo.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author senn
 * @since 2023/4/2 19:50
 **/
@Mapper
public interface UserMapper {
    User getUserById(@Param("id") long id);

    User getUserByEmail(String email);

    List<User> listCompanyUsers(long companyId, int page, int limit);

    int countCompanyUsers(long companyId);

    int addUser(User u);
}
