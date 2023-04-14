package com.demo.components.desensitized;

import com.alibaba.fastjson.serializer.ValueFilter;
import com.demo.components.desensitized.utils.DesensitizedCommonUtils;
import com.demo.components.desensitized.utils.DesensitizedValueMaskingUtils;

import java.util.Map;

/**
 * @author dewu.de
 * @date 2023-04-14 3:42 下午
 */
public class DesensitizedValueFilter implements ValueFilter {

    private final Map<Class<?>, Map<String, DesensitizedValueType>> configMap;

    public DesensitizedValueFilter(Map<Class<?>, Map<String, DesensitizedValueType>> configMap) {
        this.configMap = configMap;
    }

    @Override
    public Object process(Object object, String name, Object value) {
        if (value != null) {
            Map<String, DesensitizedValueType> desensitizedFields = configMap.get(object.getClass());
            if (DesensitizedCommonUtils.isNotEmptyMap(desensitizedFields)) {
                DesensitizedValueType valueType = desensitizedFields.get(name);
                if (valueType != null) {
                    switch (valueType) {
                        case PERSON_NAME:
                            return DesensitizedValueMaskingUtils.handlePersonName((String) value);
                        case EMAIL:
                            return DesensitizedValueMaskingUtils.handleEmail((String) value);
                        case MOBILE_PHONE:
                            return DesensitizedValueMaskingUtils.handlePhone((String) value);
                        case DETAILED_ADDRESS:
                            return DesensitizedValueMaskingUtils.handleDetailedAddress((String) value);
                        default:
                            return DesensitizedValueMaskingUtils.hidden((String) value);
                    }
                }
            }
        }
        return value;
    }

}
