package com.demo.compoments.desensitive;

import com.alibaba.fastjson.serializer.ValueFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dewu.de
 * @date 2023-04-18 2:37 下午
 */
public abstract class AbstractSensitiveValueFilter implements ValueFilter {

    private static final Logger logger = LoggerFactory.getLogger(AbstractSensitiveValueFilter.class);

    @Override
    public Object process(Object object, String name, Object value) {
        if (value != null && value.getClass() == String.class) {
            try {
                return filter(object, name, value);
            } catch (Exception e) {
                logger.error("[日志过敏] 脱敏处理异常! class={}, name={}, value={}", object.getClass().getName(), name, value);
                return value;
            }
        }
        return value;
    }

    protected abstract Object filter(Object object, String name, Object value) throws Exception;

}
