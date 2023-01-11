package com.demo.csv.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdjustRequest {

    /**
     * 待纠偏轨迹点
     */
    private GpsPoint adjustPoint;
    /**
     * 历史轨迹点
     */
    private List<GpsPoint> historyPoints;
    /**
     * 饿侧城市ID
     */
    private int cityId;
    /**
     * 骑手ID
     */
    private long knightId;
    /**
     * 设备类型
     */
    private int deviceType;
    /**
     * 端上APP版本号
     */
    private String appVersion;

}
