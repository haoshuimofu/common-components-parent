package com.demo.components.desensitive.model;

import com.demo.components.desensitive.DesensitiveValueType;
import com.demo.components.desensitive.annotation.DesensitiveField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author dewu.de
 * @date 2023-04-14 3:10 下午
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderConsumerModel {

    @DesensitiveField(valueType = DesensitiveValueType.PERSON_NAME)
    private String name;
    @DesensitiveField(valueType = DesensitiveValueType.MOBILE_PHONE)
    private String phone;
    private String province;
    private String city;
    private String district;
    @DesensitiveField(valueType = DesensitiveValueType.DETAILED_ADDRESS)
    private String address;


}
