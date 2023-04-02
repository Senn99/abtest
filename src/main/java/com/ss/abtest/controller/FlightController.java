package com.ss.abtest.controller;

import com.ss.abtest.pojo.RequestResult;
import com.ss.abtest.pojo.domain.Flight;
import com.ss.abtest.pojo.dto.FlightDto;
import com.ss.abtest.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author senn
 * @since 2023/4/2 19:50
 **/
@RestController
public class FlightController {

    @Autowired
    private FlightService flightService;

    @GetMapping("/flight/query")
    public RequestResult queryFlightWithFilter(){
        return RequestResult.successResult();
    }

    @PostMapping("/flight")
    public RequestResult createFlight(FlightDto flightDto){
        FlightDto flight = flightService.createFlight(flightDto);
        return RequestResult.successResult(flight);
    }

}
