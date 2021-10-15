package com.demo.components.shardingjdbc.model;

import java.util.Date;

/**
 * @Description Automatically generated by MBG.
 * @Author WUDE
 * @Datetime @mbg.generated 2021-08-22 14:06:05
 * @Table t_order
 */
public class OrderModel {
    /**
     * 订单ID: order_id
     * 
     * @mbg.generated 2021-08-22 14:06:05
     */
    private String orderId;

    /**
     * 用户ID: user_id
     * 
     * @mbg.generated 2021-08-22 14:06:05
     */
    private Long userId;

    /**
     * 订单标题: title
     * 
     * @mbg.generated 2021-08-22 14:06:05
     */
    private String title;

    /**
     * 订单总金额，单位分: total_amount
     * 
     * @mbg.generated 2021-08-22 14:06:05
     */
    private Long totalAmount;

    /**
     * 创建时间: create_time
     * 
     * @mbg.generated 2021-08-22 14:06:05
     */
    private Date createTime;

    /**
     * 更新时间: update_time
     * 
     * @mbg.generated 2021-08-22 14:06:05
     */
    private Date updateTime;

    /**
     * 支付时间: pay_time
     * 
     * @mbg.generated 2021-08-22 14:06:05
     */
    private Date payTime;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }
}