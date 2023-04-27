package com.demo.compoments.desensitive.annotation;

import com.demo.compoments.desensitive.SensitiveValueType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 脱敏属性标记
 *
 * @author dewu.de
 * @date 2023-04-12 4:55 下午
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface SensitiveField {

    boolean desensitized() default true;

    SensitiveValueType valueType() default SensitiveValueType.DEFAULT;

}
