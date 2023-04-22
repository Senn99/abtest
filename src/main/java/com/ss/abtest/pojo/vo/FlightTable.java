package com.ss.abtest.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.annotations.JsonAdapter;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author senn
 * @since 2023/4/20 15:35
 **/
@Data
public class FlightTable {
    private Long flightId;
    private String ownerName;
    private Long companyId;
    private String name;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
