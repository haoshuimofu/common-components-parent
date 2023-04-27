package com.demo.compoments.desensitive;

import com.demo.compoments.desensitive.utils.EmptyUtils;

import java.util.Arrays;

/**
 * @author dewu.de
 * @date 2023-04-14 2:14 下午
 */
public enum SensitiveValueType {

    /**
     * 人的姓名
     */
    PERSON_NAME,

    /**
     * 手机号码
     */
    PHONE_NUMBER,

    /**
     * 详细地址
     */
    ADDRESS,

    /**
     * 邮箱
     */
    EMAIL,

    DEFAULT;

    /**
     * 忽略大小写根据name匹配枚举
     *
     * @param name
     * @return
     */
    public static SensitiveValueType nameOf(String name) {
        if (EmptyUtils.isNotEmptyString(name)) {
            return Arrays.stream(SensitiveValueType.values())
                    .filter(vt -> vt.name().equalsIgnoreCase(name))
                    .findFirst().orElse(null);
        }
        return null;
    }

}
