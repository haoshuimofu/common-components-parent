package com.demo.components.desensitized;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.ValueFilter;

/**
 * @author dewu.de
 * @date 2023-04-14 3:21 下午
 */
public class DesensitizedLogHandler {

    private final DesensitizedConfigContainer desensitizedConfigContainer;

    public DesensitizedLogHandler(DesensitizedConfigContainer desensitizedConfigContainer) {
        this.desensitizedConfigContainer = desensitizedConfigContainer;
    }

    public String toDesensitizedJson(Object obj) {
        return JSON.toJSONString(obj, new DesensitizedValueFilter(desensitizedConfigContainer.cachedConfig()));
    }

    public String toDesensitizedJson(Object obj, ValueFilter customValueFilter) {
        return JSON.toJSONString(obj, new ValueFilter[]{new DesensitizedValueFilter(desensitizedConfigContainer.cachedConfig()), customValueFilter});

    }

}
