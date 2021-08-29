package com.demo.components.shardingjdbc.service;

import com.demo.components.shardingjdbc.dao.OrderModelDao;
import com.demo.components.shardingjdbc.model.OrderModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional(rollbackFor = Exception.class) // 默认没有事务，异常就回滚
public class OrderService {

    private final OrderModelDao orderModelDao;

    public OrderService(OrderModelDao orderModelDao) {
        this.orderModelDao = orderModelDao;
    }

    public void createOrder(OrderModel order) {
        order.setCreateTime(new Date());
        order.setUpdateTime(order.getCreateTime());
        orderModelDao.insert(order);

        order.setOrderId(String.valueOf(Integer.parseInt(order.getOrderId()) + 1));
        orderModelDao.insert(order);
//        order = null;
//        order.toString();
    }
}
