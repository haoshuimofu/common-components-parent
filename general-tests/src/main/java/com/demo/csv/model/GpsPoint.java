package com.demo.csv.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class GpsPoint {
    /**
     * 经度
     */
    private double lng;
    /**
     * 纬度
     */
    private double lat;
    /**
     * 时间戳, 必填: 端上给到的单位是ms, 算法用到的是s
     */
    private Long timestamp;
    /**
     * 速度: 有效值>=0, 其他(缺失或负值)统一归为-1
     */
    private Double speed;
    /**
     * 方向: 有效值[0,360], 其他(缺失或值不在有效值区间内)统一归为-1
     */
    private Double direction;
    /**
     * 是否需要调整
     */
    private Boolean adjusted;
    /**
     * 纠偏类型
     */
    private Integer adjustedType;
    /**
     * 方向角
     */
    private Double horizontal;
    /**
     * 高度
     */
    private Double altitude;
    /**
     * 卫星个数
     */
    private Integer satellite;
    /**
     * 定位类型
     */
    private int locationType;
    /**
     * 0: 千里眼, 1: 全链路
     */
    private Integer gpsSourceType;

}
