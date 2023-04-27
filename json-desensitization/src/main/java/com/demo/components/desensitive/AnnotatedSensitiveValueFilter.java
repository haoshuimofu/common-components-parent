package com.demo.components.desensitive;

import com.demo.components.desensitive.annotation.SensitiveField;
import com.demo.components.desensitive.utils.SensitiveValueMaskingUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author dewu.de
 * @date 2023-04-17 2:10 下午
 */
@Slf4j
public class AnnotatedSensitiveValueFilter extends AbstractSensitiveValueFilter {

    @Override
    public Object filter(Object object, String name, Object value) throws Exception {
        SensitiveField sensitiveField = object.getClass().getDeclaredField(name).getAnnotation(SensitiveField.class);
        if (sensitiveField != null && sensitiveField.desensitized()) {
            return SensitiveValueMaskingUtils.handle((String) value, sensitiveField.valueType());
        }
        return value;
    }

}
