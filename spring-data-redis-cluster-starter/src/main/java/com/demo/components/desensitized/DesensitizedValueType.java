package com.demo.components.desensitized;

import com.demo.components.desensitized.utils.DesensitizedCommonUtils;

import java.util.Arrays;

/**
 * @author dewu.de
 * @date 2023-04-14 2:14 下午
 */
public enum DesensitizedValueType {

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
    public static DesensitizedValueType nameOf(String name) {
        if (DesensitizedCommonUtils.isNotEmptyString(name)) {
            return Arrays.stream(DesensitizedValueType.values())
                    .filter(vt -> vt.name().equalsIgnoreCase(name))
                    .findFirst().orElse(null);
        }
        return null;
    }

}
