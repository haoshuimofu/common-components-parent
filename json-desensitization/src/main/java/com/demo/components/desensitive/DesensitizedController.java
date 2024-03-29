package com.demo.components.desensitive;

import com.alibaba.fastjson.JSON;
import com.demo.components.desensitive.model.OrderConsumerModel;
import com.demo.components.desensitive.model.OrderModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

/**
 * @author dewu.de
 * @date 2023-04-14 3:58 下午
 */
@RestController
@RequestMapping("desensitized")
public class DesensitizedController {

    @RequestMapping("test")
    public Object test() {
        OrderModel orderModel = new OrderModel();
        orderModel.setOrderId(UUID.randomUUID().toString());
        orderModel.setOrderNo(orderModel.getOrderId() + "_No");
        orderModel.setCreateTime(new Date());
        orderModel.setPayTime(new Date());

        OrderConsumerModel consumerModel = new OrderConsumerModel();
        consumerModel.setName("饿小宝");
        consumerModel.setPhone("17717939947");
        consumerModel.setProvince("上海");
        consumerModel.setCity("上海市");
        consumerModel.setDistrict("普陀区");
        consumerModel.setAddress("近铁城市广场N-15 101室");
        orderModel.setConsumer(consumerModel);

        System.out.println(JSON.toJSONString(orderModel));
        System.out.println(SensitiveLogUtils.toDesensitiveJson(orderModel));
        System.out.println(JSON.toJSONString(orderModel, new SensitiveValueFilter(DesensitiveConfigContainer.DESENSITIVE_CONFIG)));
        return "success";

    }

}
