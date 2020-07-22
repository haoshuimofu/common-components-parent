package com.demo.components.elasticsearch.annotation;

import java.lang.annotation.*;

/**
 * 无需持久化的field注解，一般是mappings不包含的属性需要添加
 *
 * @author wude
 * @date 2019/9/11 11:06
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ESTransient {
}