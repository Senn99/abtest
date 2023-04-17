package com.ss.abtest.pojo.domain;

import io.swagger.models.auth.In;
import lombok.Data;

import java.util.Date;
/**
 * @author senn
 * @since 2023/4/2 19:50
 **/
@Data
public class Layer {
    private Long layerId;
    private Long companyId;
    private String name;
    private Integer test;
    private String token;
    private String flowUnit;
    private Integer traffic;
    private Date createTime;
    private Date updateTime;
}
