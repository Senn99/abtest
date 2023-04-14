package com.ss.abtest.service.flow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author senn
 * @since 2023/4/14 12:37
 **/
@Component
public class FlowUpdateAdapter {

    @Autowired
    FlowDataFactory flowDataContainer;

    @Scheduled(fixedRate = 5 * 1000)
    public void times(){
        System.out.println("周期任务，执行时刻："+ LocalDateTime.now());
    }
}
