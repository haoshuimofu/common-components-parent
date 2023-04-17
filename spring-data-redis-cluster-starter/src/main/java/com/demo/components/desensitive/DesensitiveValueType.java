package com.demo.components.desensitive;

import com.demo.components.desensitive.utils.EmptyUtils;

import java.util.Arrays;

/**
 * @author dewu.de
 * @date 2023-04-14 2:14 下午
 */
public enum DesensitiveValueType {

    /**
     * 人的姓名
     */
    PERSON_NAME,

    /**
     * 手机号码
     */
    MOBILE_PHONE,

    /**
     * 详细地址
     */
    DETAILED_ADDRESS,

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
    public static DesensitiveValueType nameOf(String name) {
        if (EmptyUtils.isNotEmptyString(name)) {
            return Arrays.stream(DesensitiveValueType.values())
                    .filter(vt -> vt.name().equalsIgnoreCase(name))
                    .findFirst().orElse(null);
        }
        return null;
    }

}
