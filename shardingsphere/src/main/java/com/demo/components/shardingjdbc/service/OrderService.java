package com.demo.components.shardingjdbc.service;

import com.demo.components.shardingjdbc.dao.OrderModelDao;
import com.demo.components.shardingjdbc.model.OrderModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service
//@Transactional(rollbackFor = IllegalArgumentException.class) // 默认没有事务，异常就回滚
public class OrderService {

    private final OrderModelDao orderModelDao;

    public OrderService(OrderModelDao orderModelDao) {
        this.orderModelDao = orderModelDao;
    }

    public void createOrder(OrderModel order) {
        OrderModel existsOrder = orderModelDao.selectById(order.getOrderId());
        if (existsOrder != null) {
            log.info("订单已存在: orderId=[{}]", order.getOrderId());
            orderModelDao.deleteById(order.getOrderId());
        }
        order.setCreateTime(new Date());
        order.setUpdateTime(order.getCreateTime());
        orderModelDao.insert(order);

        int testNum = 1 / 0;
        log.error("error test: {}", testNum);

    }
}
