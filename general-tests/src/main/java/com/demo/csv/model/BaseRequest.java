package com.demo.csv.model;

import lombok.Data;

/**
 * @author dewu.de
 * @date 2022-12-07 5:21 下午
 */
@Data
public class BaseRequest {
    private int cityId;
    private String source;
    private Point origin;
    private Point dest;

}
