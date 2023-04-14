package com.ss.abtest.service;

import com.ss.abtest.pojo.domain.Flight;
import com.ss.abtest.pojo.dto.FlightDto;

import java.util.List;

public interface FlightService {
    FlightDto createFlight(FlightDto flightDto);
    List<FlightDto> listFlightByCompanyId(long companyId);
}
