package com.demo.components.desensitized.model;

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

    private String name;
    private String phone;
    private String province;
    private String city;
    private String district;
    private String address;


}
