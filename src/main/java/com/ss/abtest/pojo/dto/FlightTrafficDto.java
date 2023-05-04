package com.ss.abtest.pojo.dto;

import lombok.Data;

/**
 * @author senn
 * @since 2023/5/4 21:15
 **/
@Data
public class FlightTrafficDto {
    private Long flightId;
    private Long layerId;
    private Integer traffic;
}
