package com.ss.abtest.pojo.vo;

import com.ss.abtest.pojo.domain.User;
import com.ss.abtest.pojo.status.Position;
import lombok.Data;

/**
 * @author senn
 * @since 2023/4/2 19:50
 **/
@Data
public class FlightUser {
    private Long flightId;
    private User user;
    private Position position;
}
