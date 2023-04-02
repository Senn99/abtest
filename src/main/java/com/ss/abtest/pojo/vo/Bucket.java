package com.ss.abtest.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author senn
 * @since 2023/4/2 20:51
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bucket {
    private Long flightId;
    private Long LayerId;
    private Integer bucket;
}
