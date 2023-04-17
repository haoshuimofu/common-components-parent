package com.demo.components.desensitive;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;

/**
 * @author dewu.de
 * @date 2023-04-14 3:21 下午
 */
public class DesensitiveLogHandler {

    private final DesensitiveConfigContainer desensitizedConfigContainer;

    public DesensitiveLogHandler(DesensitiveConfigContainer desensitizedConfigContainer) {
        this.desensitizedConfigContainer = desensitizedConfigContainer;
    }

    public String toDesensitizedJson(Object obj) {
        return JSON.toJSONString(obj, new DesensitiveValueFilter(desensitizedConfigContainer.cachedConfig()), SerializerFeature.DisableCircularReferenceDetect);
    }

    public String toDesensitizedJson(Object obj, ValueFilter customValueFilter) {
        return JSON.toJSONString(obj, new ValueFilter[]{new DesensitiveValueFilter(desensitizedConfigContainer.cachedConfig()), customValueFilter}, SerializerFeature.DisableCircularReferenceDetect);

    }

}
