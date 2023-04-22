package com.ss.abtest.service;

import com.ss.abtest.pojo.dto.FlightDto;
import com.ss.abtest.pojo.dto.TableDto;
import com.ss.abtest.pojo.status.FlightStatus;
import com.ss.abtest.pojo.vo.FlightTable;

public interface FlightService {
    FlightDto createFlight(FlightDto flightDto);
    TableDto<FlightTable> listFlightByCompanyId(long companyId, int page, int limit);

    FlightDto getFlightById(String flight_id);

    void editFlightStatus(long flight_id, FlightStatus status);

    void endFlight(long flight_id);
}
