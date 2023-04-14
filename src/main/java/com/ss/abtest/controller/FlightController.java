package com.ss.abtest.controller;

import com.ss.abtest.pojo.RequestResult;
import com.ss.abtest.pojo.domain.Flight;
import com.ss.abtest.pojo.dto.FlightDto;
import com.ss.abtest.service.FlightService;
import com.ss.abtest.util.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author senn
 * @since 2023/4/2 19:50
 **/
@Api(tags = "实验相关")
@RestController
public class FlightController {

    @Autowired
    private FlightService flightService;

    @GetMapping("/company/flights")
    public String queryCompanyFlight(
            @ApiParam(value = "企业编号", required = true, example = "1")
            @RequestParam(required = true)
                    long companyId) {
        List<FlightDto> flights = flightService.listFlightByCompanyId(companyId);
        System.out.println(RequestResult.successResult(JsonUtil.parse(flights)));
        return RequestResult.successResult(flights);
    }

    @GetMapping("/flight/query")
    public RequestResult queryFlightWithFilter() {
        return RequestResult.successResult();
    }

    @PostMapping("/flight")
    public String createFlight(@RequestBody FlightDto flightDto) {
        FlightDto flight = flightService.createFlight(flightDto);
        return RequestResult.successResult(flight);
    }

}
