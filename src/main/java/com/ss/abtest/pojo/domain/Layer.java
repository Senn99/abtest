package com.ss.abtest.pojo.domain;

import io.swagger.models.auth.In;
import lombok.Data;

import java.time.LocalDateTime;
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
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
