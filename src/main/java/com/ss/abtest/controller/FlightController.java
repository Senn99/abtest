package com.ss.abtest.controller;

import com.ss.abtest.pojo.RequestResult;
import com.ss.abtest.pojo.domain.Flight;
import com.ss.abtest.pojo.domain.FlightTraffic;
import com.ss.abtest.pojo.dto.FlightDto;
import com.ss.abtest.pojo.dto.FlightTrafficDto;
import com.ss.abtest.pojo.dto.TableDto;
import com.ss.abtest.pojo.status.FlightStatus;
import com.ss.abtest.pojo.vo.FlightTable;
import com.ss.abtest.service.FlightService;
import com.ss.abtest.util.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author senn
 * @since 2023/4/2 19:50
 **/
@Api(tags = "实验相关")
@RestController
@Log4j2
public class FlightController {

    @Autowired
    private FlightService flightService;

    @GetMapping("/company/flights")
    public String queryCompanyFlight(
            @ApiParam(value = "企业编号", required = true, example = "1")
            @RequestParam(required = true)
                    long companyId,
            @ApiParam(value = "分页编号", required = true, example = "1") int page,
            @ApiParam(value = "分页大小", required = true, example = "1")
                    int limit) {
        TableDto<FlightTable> flightTableDto = flightService.listFlightByCompanyId(companyId, page, limit);
//        System.out.println(RequestResult.successResult(JsonUtil.parse(flightTableDto)));
        return RequestResult.successResult(flightTableDto);
    }

    @GetMapping("/flight/query")
    public String queryFlightWithFilter(long companyId, String name, Integer status) {
        TableDto<FlightTable> flightTableDto = flightService.listFlightWithFilter(companyId, name, status);
        return RequestResult.successResult(flightTableDto);
    }

    @GetMapping("/flight")
    public String queryFlight(String flight_id) {
        log.info("queryFlight # flight_id : {}", flight_id);
        FlightDto flight = flightService.getFlightById(flight_id);
        return RequestResult.successResult(flight);
    }

    @PostMapping("/flight")
    public String createFlight(@RequestBody FlightDto flightDto) {
        FlightDto flight = flightService.createFlight(flightDto);
        return RequestResult.successResult(flight);
//        return RequestResult.successResult(flightDto);
    }

    @PostMapping("/flight/traffic")
    public String updateFlightTraffic(@RequestBody FlightTrafficDto flightTrafficDto) {
        boolean success = flightService.updateFlightTraffic(flightTrafficDto);
        return RequestResult.successResult(success);
    }

    @GetMapping("/flight/status/test")
    public String editFlightStatus2Test(long flight_id) {
        flightService.editFlightStatus2Test(flight_id);
        return RequestResult.successResult();
    }

    @GetMapping("/flight/status/run")
    public String editFlightStatus2Run(long flight_id) {
        flightService.editFlightStatus2Run(flight_id);
        return RequestResult.successResult();
    }

    @GetMapping("/flight/status/pause")
    public String editFlightStatus2Pause(long flight_id) {
        flightService.editFlightStatus2Pause(flight_id);
        return RequestResult.successResult();
    }

    @GetMapping("/flight/status/end")
    public String editFlightStatus2End(long flight_id) {
        flightService.endFlight(flight_id);
        return RequestResult.successResult();
    }


}
