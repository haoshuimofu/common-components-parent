package com.demo.components.shardingjdbc.controller;

import com.demo.components.JsonResult;
import com.demo.components.shardingjdbc.model.OrderModel;
import com.demo.components.shardingjdbc.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("create")
    public JsonResult createOrder(@RequestBody OrderModel order) {
        return JsonResult.success("test");
    }

}
