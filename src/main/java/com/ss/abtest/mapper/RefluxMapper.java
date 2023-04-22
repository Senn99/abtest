package com.ss.abtest.mapper;

import com.ss.abtest.pojo.domain.Reflux;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author senn
 * @since 2023/4/17 16:31
 **/
@Mapper
public interface RefluxMapper {
    List<Reflux> getRefluxInterface();
}
