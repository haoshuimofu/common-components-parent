package com.demo.components.desensitive;

import com.alibaba.fastjson.serializer.ValueFilter;
import com.demo.components.desensitive.utils.EmptyUtils;
import com.demo.components.desensitive.utils.SensitiveValueMaskingUtils;

import java.util.Map;

/**
 * @author dewu.de
 * @date 2023-04-14 3:42 下午
 */
public class DesensitiveValueFilter implements ValueFilter {

    private final Map<Class<?>, Map<String, SensitiveValueType>> configMap;

    public DesensitiveValueFilter(Map<Class<?>, Map<String, SensitiveValueType>> configMap) {
        this.configMap = configMap;
    }

    @Override
    public Object process(Object object, String name, Object value) {
        System.err.println("class=" + object.getClass().getName() + ", field=" + name);
        if (value != null) {
            Map<String, SensitiveValueType> desensitizedFields = configMap.get(object.getClass());
            if (EmptyUtils.isNotEmptyMap(desensitizedFields)) {
                SensitiveValueType valueType = desensitizedFields.get(name);
                if (valueType != null) {
                    return SensitiveValueMaskingUtils.handle((String) value, valueType);
                }
            }
        }
        return value;
    }

}
