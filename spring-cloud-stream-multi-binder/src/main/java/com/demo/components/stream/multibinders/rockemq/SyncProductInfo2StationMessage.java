package com.demo.components.stream.multibinders.rockemq;

import java.util.List;

/**
 * 商品主信息冗余至服务站商品索引消息体
 *
 * @author wude
 * @date 2020/3/6 10:32
 */
public class SyncProductInfo2StationMessage {

    private List<String> productIds;
    private Long triggerTime;
    private int retry;

    public List<String> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<String> productIds) {
        this.productIds = productIds;
    }

    public Long getTriggerTime() {
        return triggerTime;
    }

    public void setTriggerTime(Long triggerTime) {
        this.triggerTime = triggerTime;
    }

    public int getRetry() {
        return retry;
    }

    public void setRetry(int retry) {
        this.retry = retry;
    }
}