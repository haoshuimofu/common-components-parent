package com.demo.components.desensitive.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @author dewu.de
 * @date 2023-04-14 3:09 下午
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderModel {

    private String orderId;
    private String orderNo;
    private Date createTime;
    private Date payTime;

    private OrderConsumerModel consumer;


}
