package com.ss.abtest.mapper;


import com.ss.abtest.pojo.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author senn
 * @since 2023/4/2 19:50
 **/
@Mapper
public interface UserMapper {
    User getUserById(@Param("id") long id);

    User getUserByEmail(String email);
}
