package com.demo.components.desensitive;

import com.alibaba.fastjson.serializer.ValueFilter;
import com.demo.components.desensitive.utils.DesensitizedCommonUtils;
import com.demo.components.desensitive.utils.DesensitizedValueMaskingUtils;

import java.util.Map;

/**
 * @author dewu.de
 * @date 2023-04-14 3:42 下午
 */
public class DesensitiveValueFilter implements ValueFilter {

    private final Map<Class<?>, Map<String, DesensitiveValueType>> configMap;

    public DesensitiveValueFilter(Map<Class<?>, Map<String, DesensitiveValueType>> configMap) {
        this.configMap = configMap;
    }

    @Override
    public Object process(Object object, String name, Object value) {
        if (value != null && value.getClass() == String.class) {
            Map<String, DesensitiveValueType> desensitizedFields = configMap.get(object.getClass());
            if (DesensitizedCommonUtils.isNotEmptyMap(desensitizedFields)) {
                DesensitiveValueType valueType = desensitizedFields.get(name);
                if (valueType != null) {
                    return DesensitizedValueMaskingUtils.handle((String) value, valueType);
                }
            }
        }
        return value;
    }

}
