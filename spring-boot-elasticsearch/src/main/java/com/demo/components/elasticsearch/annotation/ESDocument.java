package com.demo.components.elasticsearch.annotation;

import java.lang.annotation.*;

/**
 * ES索引模型类注解，指定其对应索引名称
 *
 * @author wude
 * @date 2019-07-23 19:27
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ESDocument {

    /**
     * 索引名称
     *
     * @return
     */
    String index();

    /**
     * 定义索引结构(mappings)和(settings)的json文件位置
     *
     * @return
     */
    String schema() default "";

    /**
     * 当传入_id值为空时是否允许elasticsearch自动为其生成_id
     *
     * @return
     */
    boolean autoId() default false;

    /**
     * join-field定义
     *
     * @return
     */
    JoinField join();

}