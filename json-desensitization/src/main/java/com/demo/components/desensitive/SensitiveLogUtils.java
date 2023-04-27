package com.demo.components.desensitive;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.ValueFilter;

/**
 * @author dewu.de
 * @date 2023-04-24 10:38 上午
 */
public class SensitiveLogUtils {

    public static String toDesensitiveJson(Object obj) {
//        return JSON.toJSONString(obj, new SensitiveValueFilter(DesensitiveConfigContainer.DESENSITIVE_CONFIG), SerializerFeature.DisableCircularReferenceDetect);
        return JSON.toJSONString(obj, new SensitiveValueFilter(DesensitiveConfigContainer.DESENSITIVE_CONFIG));
    }

    public static String toDesensitiveJson(Object obj, ValueFilter valueFilter) {
        return JSON.toJSONString(obj, new ValueFilter[]{new SensitiveValueFilter(DesensitiveConfigContainer.DESENSITIVE_CONFIG), valueFilter});
    }

}
