package com.ss.abtest.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Long userId;
    private String Name;
    private Integer sex;
    private String email;
    private String password;
    private Integer status;
    private Long companyId;
    private Integer companyUserStatus;
    private Date createTime;
    private Date updateTime;
}
