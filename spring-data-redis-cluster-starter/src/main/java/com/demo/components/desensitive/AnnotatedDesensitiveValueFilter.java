package com.demo.components.desensitive;

import com.alibaba.fastjson.serializer.ValueFilter;
import com.demo.components.desensitive.annotation.DesensitiveField;
import com.demo.components.desensitive.utils.SensitiveValueMaskingUtils;

/**
 * @author dewu.de
 * @date 2023-04-17 2:10 下午
 */
public class AnnotatedDesensitiveValueFilter implements ValueFilter {

    @Override
    public Object process(Object object, String name, Object value) {

        if (value != null && value.getClass() == String.class) {
            try {
                DesensitiveField dd = value.getClass().getDeclaredField(name).getAnnotation(DesensitiveField.class);
                if (dd != null && dd.desensitized()) {
                    return SensitiveValueMaskingUtils.handle((String) value, dd.valueType());
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
