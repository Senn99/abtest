package com.ss.abtest.service;

import com.ss.abtest.pojo.domain.Flight;
import com.ss.abtest.pojo.dto.FlightDto;
import com.ss.abtest.pojo.dto.FlightTrafficDto;
import com.ss.abtest.pojo.dto.TableDto;
import com.ss.abtest.pojo.status.FlightStatus;
import com.ss.abtest.pojo.vo.FlightTable;

public interface FlightService {
    FlightDto createFlight(FlightDto flightDto);
    TableDto<FlightTable> listFlightByCompanyId(long companyId, int page, int limit);

    FlightDto getFlightById(String flight_id);

    void editFlightStatus2Run(long flight_id);

    void editFlightStatus2Pause(long flight_id);

    void editFlightStatus2Test(long flight_id);

    void endFlight(long flight_id);

    TableDto<FlightTable> listFlightWithFilter(long companyId, String name, Integer status);

    boolean updateFlightTraffic(FlightTrafficDto flightTrafficDto);
}
