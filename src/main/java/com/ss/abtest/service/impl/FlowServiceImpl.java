package com.ss.abtest.service.impl;

import com.ss.abtest.mapper.FlowMapper;
import com.ss.abtest.pojo.domain.Version;
import com.ss.abtest.pojo.flow.*;
import com.ss.abtest.service.FlowService;
import com.ss.abtest.service.flow.FlowDataContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
     * @param request 请求
     * @return FlowResponse 分流响应
     */
    @Override
    public FlowResponse getFlightFlow(FlowRequest request) {
        // 1、参数验证。
        request.verifyParams();
        //2、获取实验层。同一个token下可有多个实验层
        List<FlowData> layerList = flowDataContainer.getLayer(request);
        FlowResponse response = FlowResponse.nullResponse();
        response.setFlowId(System.currentTimeMillis());
        for (FlowData layer : layerList) {
            //3、检查实验层上是否有测试用户
            if (layer.isNeedTestUser()) {
                // 判断  用户是否存在于测试用户
                FlowResponse.nullResponse();
                continue;
            }
            //4、流量分桶。
            int bucket = flowBucket(request, layer);
            //5、检验对应桶是否存在实验
            if (!layer.checkBucketExist(bucket)) {
                continue;
            }
            FlightVersion flightVersion = layer.getFlightVersion(bucket);
            //5、检验过滤条件。
            if (!matchFlightFiler(request, flightVersion)) {
                continue;
            }
            //6、计算实验分组。
            int group = flowVersion(request, flightVersion);
            //7、设置此次命中实验配置。
            Version version = flightVersion.getVersion(group);
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
     * 拼接流量分桶的hash key
     * @param request 分流请求
     * @param layer 实验层
     * @return bucket 桶号
     */
    private int flowBucket(FlowRequest request, FlowData layer) {
        //1、拼接 hashKey;
        String key = String.format(UNIT_KEY, request.getUnitValue(), layer.getLayerName());
        return key.hashCode() % UNIT_BUCKET_LENGTH;
    }

    /**
     * 拼接实验组分组的hash key
     * @param request 分流请求
     * @param flightVersion 实验组
     * @return bucket 组号
     */
    private int flowVersion(FlowRequest request, FlightVersion flightVersion) {
        //1、拼接 hashKey;
        String key = String.format(UNIT_KEY, request.getUnitValue(), flightVersion.getFlightName());
        return key.hashCode() % flightVersion.getVersionSize();
    }
}
