package com.ss.abtest.mapper;


import com.ss.abtest.pojo.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User getUserById(long id);
}
