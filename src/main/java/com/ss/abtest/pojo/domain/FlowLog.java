package com.ss.abtest.pojo.domain;

import lombok.Data;

import java.util.Date;

@Data
public class FlowLog {
    private Long id;
    private Long FlowId;
    private String request;
    private Integer Status;
    private Date createTime;
}
