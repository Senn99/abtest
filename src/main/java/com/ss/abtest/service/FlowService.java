package com.ss.abtest.service;

import com.ss.abtest.pojo.flow.FlowRequest;
import com.ss.abtest.pojo.flow.FlowResponse;

/**
 * @author senn
 * @since 2023/4/4 10:40
 **/
public interface FlowService {
    /**
     * 获取分流信息
     * @param request 请求
     * @return 响应
     */
    FlowResponse getFlightFlow(FlowRequest request);
}
