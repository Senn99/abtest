package com.ss.abtest.service;

import com.ss.abtest.pojo.domain.Flight;
import com.ss.abtest.pojo.dto.FlightDto;

public interface FlightService {
    FlightDto createFlight(FlightDto flightDto);
}
