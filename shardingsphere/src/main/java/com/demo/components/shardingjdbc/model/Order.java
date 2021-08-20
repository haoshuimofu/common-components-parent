package com.demo.components.shardingjdbc.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * 订单表实体类
 *
 * @author eleme
 * @datetime 2021-08-19 下午5:33
 */
@Data
@Builder
public class Order {

    private String orderId;
    private String userId;
    private String title;
    private Double totalAmount;
    private Date createTime;
    private Date payTime;

}
