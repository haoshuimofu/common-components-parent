package com.demo.components.desensitive;

import com.demo.components.desensitive.utils.EmptyUtils;
import com.demo.components.desensitive.utils.SensitiveValueMaskingUtils;

import java.util.Map;

/**
 * @author dewu.de
 * @date 2023-04-14 3:42 下午
 */
public class SensitiveValueFilter extends AbstractSensitiveValueFilter {

    private final Map<Class<?>, Map<String, SensitiveValueType>> configMap;

    public SensitiveValueFilter(Map<Class<?>, Map<String, SensitiveValueType>> configMap) {
        this.configMap = configMap;
    }

    @Override
    public Object filter(Object object, String name, Object value) {
        Map<String, SensitiveValueType> desensitizedFields = configMap.get(object.getClass());
        if (EmptyUtils.isNotEmptyMap(desensitizedFields)) {
            SensitiveValueType valueType = desensitizedFields.get(name);
            if (valueType != null) {
                return SensitiveValueMaskingUtils.handle((String) value, valueType);
            }
        }
        return value;
    }

}
