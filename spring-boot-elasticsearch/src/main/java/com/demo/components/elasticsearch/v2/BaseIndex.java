package com.demo.components.elasticsearch.v2;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dewu.de
 * @date 2024-06-07 4:28 下午
 */
@Getter
@Setter
public class BaseIndex {

    @JSONField(serialize = false, deserialize = false)
    private String id;
    @JSONField(serialize = false, deserialize = false)
    private String routing;

}
