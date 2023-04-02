package com.ss.abtest.pojo.domain;

import lombok.Data;

import java.util.Date;
/**
 * @author senn
 * @since 2023/4/2 19:50
 **/
@Data
public class FlowLog {
    private Long id;
    private Long FlowId;
    private String request;
    private Integer Status;
    private Date createTime;
}
