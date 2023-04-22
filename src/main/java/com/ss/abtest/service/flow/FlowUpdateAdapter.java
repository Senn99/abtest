package com.ss.abtest.service.flow;

import com.ss.abtest.mapper.FlightMapper;
import com.ss.abtest.mapper.LayerMapper;
import com.ss.abtest.pojo.domain.*;
import com.ss.abtest.pojo.flow.FlightVersion;
import com.ss.abtest.pojo.flow.FlowData;
import com.ss.abtest.pojo.status.FlightStatus;
import com.ss.abtest.pojo.status.FlowUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author senn
 * @since 2023/4/14 12:37
 **/
@Component
public class FlowUpdateAdapter {

    @Autowired
    FlowDataFactory flowDataContainer;

    @Autowired
    LayerMapper layerMapper;

    @Autowired
    FlightMapper flightMapper;

//    @Scheduled(fixedRate = 60 * 1000)
    public void times() {
        System.out.println("周期任务，执行时刻：" + LocalDateTime.now());
        Map<String, Map<String, FlowData>> flowDataMap = new ConcurrentHashMap<>();
        // 1、获取所有的实验层。
        List<Layer> layers = layerMapper.listLayer();
        for (Layer layer : layers) {
            FlowData flowData = new FlowData();
            String token = layer.getToken();
            String flowUnit = layer.getFlowUnit();
            Long layerId = layer.getLayerId();
            flowData.setLayerId(layerId);
            flowData.setCompanyId(layer.getCompanyId());
            flowData.setFlowUnit(FlowUnit.getStatus(flowUnit));
            flowData.setLayerName(layer.getName());
            // 2、获取实验层分桶数据
            List<FlightTraffic> flightTrafficList = layerMapper.getFlightTraffic(layerId);
            // 实验缓存，因为一个实验有多个桶，不需要每次都查询数据库。
            Map<Long, FlightVersion> cache = new HashMap<>();

            Map<Integer, FlightVersion> flightVersionMap = new HashMap<>();
            for (FlightTraffic flightTraffic : flightTrafficList) {
                Long flightId = flightTraffic.getFlightId();
                if (cache.containsKey(flightId)) {
                    flightVersionMap.put(flightTraffic.getBucket(), cache.get(flightId));
                    continue;
                }
                // 3、获取实验
                Flight flight =  flightMapper.getFlightById(flightId);
                FlightVersion flightVersion = new FlightVersion();
                flightVersion.setFlightName(flight.getName());
                flightVersion.setFlightFilter(flight.getFilterMap());
                flightVersion.setFlightStatus(FlightStatus.getStatus(flight.getStatus()));
                // 4、设置实验组
                List<Version> versions = flightMapper.getVersionByFlightId(flightId);
                Map<Integer, Version> versionMap = new HashMap<>();
                int index = 0;
                for (Version version : versions) {
                    versionMap.put(index++, version);
                }
                flightVersion.setVersionSize(versions.size());
                flightVersion.setVersions(versionMap);
                // 5、存入缓存 用于下次处理
                cache.put(flightId, flightVersion);
                // 6、存入分桶容器。实验创建、暂停、结束 则不需要分流
                if (flight.getStatus() == FlightStatus.NORMAL.getValue()) {
                    flightVersionMap.put(flightTraffic.getBucket(), flightVersion);
                }
            }
            // 7、设置测试用户，cache有所有的实验信息。
            flowData.setNeedTestUser(layer.getTest() > 0);
            if (layer.getTest() > 0) {
                Map<String, FlightVersion> testUserMap = new HashMap<>();
                // 8、获取所有测试用户
                List<FlightTestUser> flightTestUsers = flightMapper.listFlightTestUsers(layerId);
                for (FlightTestUser flightTestUser : flightTestUsers) {
                    FlightVersion flightVersion = cache.get(flightTestUser.getFlightId());
                    testUserMap.put(flightTestUser.getFlowContent(), flightVersion);
                }
                flowData.setTestUsers(testUserMap);
            }

            // 5、设置分桶数据
            flowData.setBucket(flightVersionMap);
            // 6、将数据放入 分流容器中
            Map<String, FlowData> map = flowDataMap.getOrDefault(token, new ConcurrentHashMap<>());
            map.put(flowUnit, flowData);
            flowDataMap.put(token, map);
        }
        flowDataContainer.setFlowDataMap(flowDataMap);
    }
}
