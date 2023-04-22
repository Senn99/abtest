package com.ss.abtest.service.reflux;

import com.ss.abtest.mapper.FlowMapper;
import com.ss.abtest.mapper.RefluxMapper;
import com.ss.abtest.pojo.domain.FlowLog;
import com.ss.abtest.pojo.domain.Reflux;
import com.ss.abtest.util.HttpUtil;
import com.ss.abtest.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author senn
 * @since 2023/4/17 16:29
 **/
//@Component
public class RefluxHandler {

    @Autowired
    RefluxMapper refluxMapper;

    @Autowired
    FlowMapper flowMapper;


//    @Scheduled(fixedRate = 1 * 1000)
    public void refluxTime() {
        System.out.println("周期回流任务..time: " + LocalDateTime.now());
        // 1、获取状态正常的接口
        List<Reflux> refluxInterface = refluxMapper.getRefluxInterface();
        if (refluxInterface == null || refluxInterface.isEmpty()) {
            return;
        }
        // 2、遍历发送
        for (Reflux reflux : refluxInterface) {
            //3、获取对应的日志
            List<FlowLog> flowLog = flowMapper.getFlowLog(reflux.getCompanyId(), reflux.getTqs());
            if (flowLog != null && !flowLog.isEmpty()) {
                String parse = JsonUtil.parse(flowLog);
                HttpUtil.postMethod(reflux.getUrl(), parse);
                //4、更新日志状态
                flowMapper.updateLogStatus(flowLog);
            }

        }
    }
}
