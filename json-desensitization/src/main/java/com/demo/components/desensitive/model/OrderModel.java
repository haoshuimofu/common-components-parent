package com.demo.components.desensitive.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.demo.components.desensitive.AnnotatedSensitiveValueFilter;
import com.demo.components.desensitive.annotation.SensitiveField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

/**
 * @author dewu.de
 * @date 2023-04-14 3:09 下午
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderModel {

    @SensitiveField
    private String orderId;
    @SensitiveField
    private String orderNo;
    @JSONField(serialize = false)
    private Date createTime;
    private Date payTime;
    private OrderConsumerModel consumer;

    public static void main(String[] args) {
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
//        consumerModel.setOrder(orderModel); // 循环依赖测试


        orderModel.setConsumer(consumerModel);
//        orderModel.setSecondConsumer(consumerModel);

//        System.out.println(JSON.toJSONString(orderModel, SerializerFeature.DisableCircularReferenceDetect));
//        System.out.println(JSON.toJSONString(orderModel, new AnnotatedSensitiveValueFilter(), SerializerFeature.DisableCircularReferenceDetect));
        System.out.println();
        System.out.println(JSON.toJSONString(orderModel));
        System.out.println(JSON.toJSONString(orderModel, new AnnotatedSensitiveValueFilter()));

    }


}
