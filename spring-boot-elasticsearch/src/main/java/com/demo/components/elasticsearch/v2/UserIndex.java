package com.demo.components.elasticsearch.v2;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dewu.de
 * @date 2024-06-19 10:40 上午
 */
@Getter
@Setter
public class UserIndex extends BaseIndex {

    @JSONField(name = "id_card")
    private String idCard;
    @JSONField(name = "name")
    private String name;
    @JSONField(name = "age")
    private int age;

}
