package com.ss.abtest.controller;

import com.ss.abtest.pojo.RequestResult;
import com.ss.abtest.pojo.flow.FlowRequest;
import com.ss.abtest.pojo.flow.FlowResponse;
import com.ss.abtest.service.FlowService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author senn
 * @since 2023/4/4 10:41
 **/
@Api(tags = "分流相关")
@RestController
public class FlowController {

    @Autowired
    FlowService flowService;

    @GetMapping("/flow")
    public String flow(FlowRequest request) {
        try {
            FlowResponse flowResponse = flowService.getFlightFlow(request);
            return RequestResult.successResult(flowResponse);
        }catch (Exception e) {
            return RequestResult.requestErrorResult(e.getMessage());
        }
    }

    @PostMapping("/flow")
    public String postFlow(@RequestBody FlowRequest request) {
        try {
            FlowResponse flowResponse = flowService.getFlightFlow(request);
            return RequestResult.successResult(flowResponse);
        }catch (Exception e) {
            return RequestResult.requestErrorResult(e.getMessage());
        }
    }

}
