package com.ss.abtest.mapper;

import com.ss.abtest.pojo.flow.FlowResponse;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author senn
 * @since 2023/4/4 16:11
 **/
@Mapper
public interface FlowMapper {
    int addFlowLog(FlowResponse response);
}
