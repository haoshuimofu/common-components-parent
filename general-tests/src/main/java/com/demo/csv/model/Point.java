package com.demo.csv.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author dewu.de
 * @date 2022-12-07 5:26 下午
 */
@Data
@AllArgsConstructor
public class Point {
    private double lng;
    private double lat;
}
