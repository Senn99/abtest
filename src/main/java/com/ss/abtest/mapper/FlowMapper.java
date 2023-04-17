package com.ss.abtest.mapper;

import com.ss.abtest.pojo.domain.FlowLog;
import com.ss.abtest.pojo.flow.FlowResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author senn
 * @since 2023/4/4 16:11
 **/
@Mapper
public interface FlowMapper {
    int addFlowLog(FlowResponse response);
    List<FlowLog> getFlowLog(Long companyId, int size);

    void updateLogStatus(List<FlowLog> list);
}
