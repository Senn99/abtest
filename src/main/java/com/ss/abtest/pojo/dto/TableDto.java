package com.ss.abtest.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author senn
 * @since 2023/4/20 15:15
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableDto<T> {
    private List<T> list;
    private int total;
}
