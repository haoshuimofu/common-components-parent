package com.demo.components.elasticsearch.annotation;

import java.lang.annotation.*;

/**
 * join-field定义, 参考：Join datatype | Elasticsearch Reference [7.6] | Elastic
 * https://www.elastic.co/guide/en/elasticsearch/reference/current/parent-join.html
 *
 * @author wude
 * @date 2020/2/16 15:50
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JoinField {

    /**
     * 类型
     *
     * @return
     */
    JoinFieldTypeEnum type();

    /**
     * mappings里join-field属性名称
     *
     * @return
     */
    String field() default "";

    /**
     * 当前索引doc对应join-field里的name
     *
     * @return
     */
    String name() default "";

}