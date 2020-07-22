package com.demo.components.elasticsearch.annotation;

import java.lang.annotation.*;

/**
 * ES索引模型类属性注解，用于指定此属性在mappings中实际field名称
 *
 * @author wude
 * @date 2019-07-11 17:08
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ESField {

    String name() default "";

    DataTypeEnum datatype() default DataTypeEnum.CORE;

}