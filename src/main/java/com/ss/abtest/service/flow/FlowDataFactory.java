package com.ss.abtest.service.flow;

import com.ss.abtest.pojo.flow.FlightVersion;
import com.ss.abtest.pojo.flow.FlowData;
import com.ss.abtest.pojo.flow.FlowRequest;

import java.util.List;
import java.util.Map;

/**
 * @author senn
 * @since 2023/4/4 11:00
 **/
public interface FlowDataFactory {
    List<FlowData> getLayer(FlowRequest token);
    void setFlowData(String token, String flowUnit, List<FlowData> flowData);
    void setFlightVersion(String token, long layerId, Map<Integer, FlightVersion> flightVersion);
}
