package com.demo.components.elasticsearch.annotation;

import java.lang.annotation.*;

/**
 * parent-child / join-field 用在在child一方Model类中, 以标注实际值为parent值的属性
 *
 * @author wude
 * @date 2020/2/16 19:50
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ESParent {
}