package com.ss.abtest.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author senn
 * @since 2023/4/2 20:44
 **/
@Mapper
public interface LayerMapper {
    int updateLayerTraffic(int flightTraffic);


    int getTraffic(@Param("id") Long id);
}
