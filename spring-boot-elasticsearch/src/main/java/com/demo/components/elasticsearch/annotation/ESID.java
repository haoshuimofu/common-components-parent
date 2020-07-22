package com.demo.components.elasticsearch.annotation;

import java.lang.annotation.*;

/**
 * 索引模型ID，对应索引记录_id
 *
 * @author wude
 * @date 2019-07-16 13:57
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ESID {
}