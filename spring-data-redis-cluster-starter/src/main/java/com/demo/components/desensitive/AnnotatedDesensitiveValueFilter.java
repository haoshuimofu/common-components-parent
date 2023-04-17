package com.demo.components.desensitive;

import com.alibaba.fastjson.serializer.ValueFilter;
import com.demo.components.desensitive.annotation.DesensitiveField;
import com.demo.components.desensitive.utils.SensitiveValueMaskingUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author dewu.de
 * @date 2023-04-17 2:10 下午
 */
@Slf4j
public class AnnotatedDesensitiveValueFilter implements ValueFilter {

    @Override
    public Object process(Object object, String name, Object value) {
        if (value != null) {
            try {
                DesensitiveField dd = object.getClass().getDeclaredField(name).getAnnotation(DesensitiveField.class);
                if (dd != null && dd.desensitized()) {
                    return SensitiveValueMaskingUtils.handle((String) value, dd.valueType());
                }
                return value;
            } catch (NoSuchFieldException e) {
                log.info("NoSuchFieldException: class={}, field={}", object.getClass().getName(), name);
            }
        }
        return null;
    }

}
