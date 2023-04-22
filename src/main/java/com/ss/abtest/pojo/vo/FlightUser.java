package com.ss.abtest.pojo.vo;

import com.ss.abtest.pojo.domain.User;
import com.ss.abtest.pojo.status.Position;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author senn
 * @since 2023/4/2 19:50
 **/
@Data
public class FlightUser {
    private Long flightId;
    private Long userId;
    private String name;
    private Integer position;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public FlightUser() {
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }

    public Long getUser_id() {
        return userId;
    }

    public Long getFlight_id() {
        return flightId;
    }

    public User getUser() {
        User user = new User();
        user.setUserId(userId);
        user.setName(name);
        return user;
    }
}
