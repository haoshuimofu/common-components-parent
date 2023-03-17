package com.demo.components.shardingjdbc.service;

import com.demo.components.shardingjdbc.dao.OrderModelDao;
import com.demo.components.shardingjdbc.model.OrderModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * DB操作和事务测试
 * <p>问题1: 系统自动创建TransactionManager, mvn tree显示spring-tx包是mybatis-spring-boot-starter引入的</p>
 * <p>启动时debug类: DataSourceTransactionManager, 最终会调到其无参构造方法，往前追随到{@link org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration}</p>
 */
@Slf4j
@Service
@Transactional(rollbackFor = IllegalArgumentException.class)
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

        String orderId1 = String.valueOf(Integer.parseInt(order.getOrderId()) + 1);
        OrderModel existsOrder1 = orderModelDao.selectById(orderId1);
        if (existsOrder1 != null) {
            log.info("订单已存在: orderId=[{}]", order.getOrderId());
            orderModelDao.deleteById(orderId1);
        }
        order.setOrderId(orderId1);
        orderModelDao.insert(order);

        int testNum = 1 / 0;
        log.error("error test: {}", testNum);

    }
}
