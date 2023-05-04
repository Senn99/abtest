package com.ss.abtest.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ss.abtest.exception.IllegalParamException;
import com.ss.abtest.pojo.status.RefluxStatus;
import lombok.Data;
import org.apache.logging.log4j.util.Strings;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author senn
 * @since 2023/4/26 16:04
 **/
@Data
public class RefluxDto {
    private Long refluxId;
    private Long companyId;
    private String url;
    private Integer tqs;
    private Integer status;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    public void checkParamNotNull() {
        if (Strings.isEmpty(url)) {
            throw new IllegalParamException("url is empty.. url: " + url);
        }
        if (tqs == null) {
            throw new IllegalParamException("tqs is null.. tqs: " + tqs);
        }
        if (status == null || !RefluxStatus.checkStatus(status)) {
            throw new IllegalParamException("status is error .. status: " + status);
        }
        if (createTime == null) {
            createTime = LocalDateTime.now();
        }
        if (updateTime == null) {
            updateTime = LocalDateTime.now();
        }
    }

    public boolean isIdEmpty() {
        return refluxId == null;
    }
}
