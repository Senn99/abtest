package com.ss.abtest.service.flow;

import com.ss.abtest.pojo.flow.FlowData;
import com.ss.abtest.pojo.flow.FlowRequest;

import java.util.Map;

/**
 * @author senn
 * @since 2023/4/4 11:00
 **/
public interface FlowDataFactory {
    FlowData getLayer(FlowRequest token);

    void setFlowDataMap(Map<String, Map<String, FlowData>> map);
}
