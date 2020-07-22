package com.demo.components.stream.rabbit.message.pollable;

import java.util.Date;
import java.util.List;

/**
 * 商品索引构建后同步信息至服务站商品索引
 *
 * @author wude
 * @date 2020/3/6 10:32
 */
public class SyncProduct2StationMessage {

    private Date triggerTime;
    private List<String> productIds;

    public Date getTriggerTime() {
        return triggerTime;
    }

    public void setTriggerTime(Date triggerTime) {
        this.triggerTime = triggerTime;
    }

    public List<String> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<String> productIds) {
        this.productIds = productIds;
    }
}