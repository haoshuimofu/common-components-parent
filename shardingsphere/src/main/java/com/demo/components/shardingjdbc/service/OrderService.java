package com.demo.components.shardingjdbc.service;

import com.demo.components.shardingjdbc.dao.OrderModelDao;
import com.demo.components.shardingjdbc.model.OrderModel;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderService {

    private final OrderModelDao orderModelDao;

    public OrderService(OrderModelDao orderModelDao) {
        this.orderModelDao = orderModelDao;
    }

    public void createOrder(OrderModel order) {
        order.setCreateTime(new Date());
        order.setUpdateTime(order.getCreateTime());
        orderModelDao.insert(order);
    }
}
