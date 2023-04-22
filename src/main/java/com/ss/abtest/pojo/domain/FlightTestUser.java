package com.ss.abtest.pojo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author senn
 * @since 2023/4/16 20:48
 **/
@Data
@AllArgsConstructor
public class FlightTestUser {
    private Long id;
    private Long flightId;
    private String flowContent;
    private LocalDateTime createTime;

    public FlightTestUser() {
        createTime = LocalDateTime.now();
    }

    public FlightTestUser(Long flightId, String flowContent) {
        this.flightId = flightId;
        this.flowContent = flowContent;
        createTime = LocalDateTime.now();
    }
}
