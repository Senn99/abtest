package com.ss.abtest.service.flow;

import com.ss.abtest.pojo.flow.FlowData;
import com.ss.abtest.pojo.flow.FlowRequest;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author senn
 * @since 2023/4/4 10:58
 **/
@Component
public class FlowDataContainer implements FlowDataFactory {

    // 数据中心 token -> Map<String, FlowData> -> floUnit ->  FlowData  一个 token 可对应一组实验层。
    private Map<String, Map<String, FlowData>> flowDataMap = new ConcurrentHashMap<>();

    @Override
    public FlowData getLayer(FlowRequest request) {
        Map<String, FlowData> stringFlowDataMap = flowDataMap.getOrDefault(request.getToken(), new HashMap<>());
        return stringFlowDataMap.getOrDefault(request.getFlowUnit(), null);
    }

    @Override
    public void setFlowDataMap(Map<String, Map<String, FlowData>> map) {
        this.flowDataMap = map;
    }

}
