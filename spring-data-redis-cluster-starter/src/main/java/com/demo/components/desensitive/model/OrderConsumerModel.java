package com.demo.components.desensitive.model;

import com.demo.components.desensitive.SensitiveValueType;
import com.demo.components.desensitive.annotation.SensitiveField;
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

    @SensitiveField(valueType = SensitiveValueType.PERSON_NAME)
    private String name;
    @SensitiveField(valueType = SensitiveValueType.PHONE_NUMBER)
    private String phone;
    private String province;
    private String city;
    private String district;
    @SensitiveField(valueType = SensitiveValueType.ADDRESS)
    private String address;


}
