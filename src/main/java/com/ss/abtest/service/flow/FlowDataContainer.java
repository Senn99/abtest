package com.ss.abtest.service.flow;

import com.ss.abtest.pojo.flow.FlightVersion;
import com.ss.abtest.pojo.flow.FlowData;
import com.ss.abtest.pojo.flow.FlowRequest;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author senn
 * @since 2023/4/4 10:58
 **/
@Component
public class FlowDataContainer implements FlowDataFactory {

    // 数据中心 token -> Map<String, FlowData> -> floUnit ->  FlowData  一个 token 可对应一组实验层。
    private final Map<String, Map<String, List<FlowData>>> flowDataMap = new ConcurrentHashMap<>();

    @Override
    public List<FlowData> getLayer(FlowRequest request) {
        Map<String, List<FlowData>> stringFlowDataMap = flowDataMap.get(request.getToken());
        return stringFlowDataMap.get(request.getUnitValue());
    }

    @Override
    public void setFlowData(String token, String flowUnit, List<FlowData> flowData) {
        Map<String, List<FlowData>> stringListMap = flowDataMap.get(token);
        stringListMap.put(flowUnit, flowData);
    }

    @Override
    public void setFlightVersion(String token, long layerId, Map<Integer, FlightVersion> flightVersionMap) {
        Map<String, List<FlowData>> flowData = flowDataMap.getOrDefault(token, new HashMap<>());
        FlowData fd = null;
        for (List<FlowData> list : flowData.values()) {
            for (FlowData data : list) {
                if (data.getLayerId() == layerId) {
                    fd = data;
                    break;
                }
            }
        }
        if (fd != null) {
            Map<Integer, FlightVersion> bucket = fd.getBucket();
            bucket.putAll(flightVersionMap);
        }
    }

}
