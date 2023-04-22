package com.ss.abtest.service.impl;

import com.ss.abtest.mapper.FlowMapper;
import com.ss.abtest.pojo.domain.Version;
import com.ss.abtest.pojo.flow.*;
import com.ss.abtest.service.FlowService;
import com.ss.abtest.service.flow.FlowDataContainer;
import com.ss.abtest.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author senn
 * @since 2023/4/4 10:49
 **/
@Service
public class FlowServiceImpl implements FlowService {

    private static final String UNIT_KEY = "%s:%s";

    private static final int UNIT_BUCKET_LENGTH = 1000;

    @Autowired
    FlowDataContainer flowDataContainer;

    @Autowired
    FlowMapper flowMapper;

    /**
     * 分流逻辑如下
     *
     * @param request 请求
     * @return FlowResponse 分流响应
     */
    @Override
    public FlowResponse getFlightFlow(FlowRequest request) {
        // 1、参数验证。
        request.verifyParams();
        //2、获取实验层。根据token与分流单位获取实验层。
        FlowData layer = flowDataContainer.getLayer(request);
        if (layer == null) {
            return FlowResponse.nullResponse("layer is null + request:" + JsonUtil.parse(request));
        }
        FlowResponse response = new FlowResponse();
        response.setFlowId(System.currentTimeMillis());
        response.setRequest(request);
        response.setCreateTime(LocalDateTime.now());
        response.setCompanyId(layer.getCompanyId());
        FlightVersion flightVersion;
        //3、检查实验层上是否有测试用户
        if (layer.isNeedTestUser() && layer.containTestUser(request)) {
            String unitValue = request.getUnitValue();
            flightVersion = layer.getTestUserFlight(unitValue);
        } else {
            //4、流量分桶。
            int bucket = flowHash(request.getUnitValue(), layer.getLayerName(), UNIT_BUCKET_LENGTH);
            //5、检验对应桶是否存在实验
            if (!layer.checkBucketExist(bucket)) {
                response.setMessage("bucket not exist.. there is no flight..");
                return response;
            }
            flightVersion = layer.getFlightVersion(bucket);
        }
        if (flightVersion == null) {
            return FlowResponse.nullResponse("flight is null. request: " + JsonUtil.parse(request));
        }
        //5、检验过滤条件。
        if (!matchFlightFiler(request, flightVersion)) {
            response.setMessage("flight filer is not match .. ");
            return response;
        }
        //6、计算实验分组。
        int group = flowHash(request.getUnitValue(), flightVersion.getFlightName(), flightVersion.getVersionSize());
        //7、设置此次命中实验配置。
        Version version = flightVersion.getVersion(group);
        if (version != null) {
            response.addVersionConfig(version.getMapConfig());
            response.addVid(version.getVersionId());
        }
        // 8、存入数据库。
        flowMapper.addFlowLog(response);
        // 9、返回分流信息。
        return response;
    }


    private boolean matchFlightFiler(FlowRequest request, FlightVersion flightVersion) {
        Map<String, String> requestConfig = request.getRequestConfig();
        return flightVersion.checkFilter(requestConfig);
    }

    /**
     *
     * @param content content
     * @param target target
     * @param length length
     * @return hash
     */
    private int flowHash(String content, String target, int length) {
        String key = String.format(UNIT_KEY, content, target);
        int hash = key.hashCode() % length;
        return hash < 0 ? -hash : hash;
    }
}
