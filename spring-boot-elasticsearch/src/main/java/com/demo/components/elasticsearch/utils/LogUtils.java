package com.demo.components.elasticsearch.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @Author wude
 * @Create 2019-08-07 14:11
 */
public class LogUtils {

    /**
     * 对象转String以便在日志中输入
     *
     * @param obj
     * @return
     */
    public static String obj2PrettyString(Object obj) {
        // return ReflectionToStringBuilder.toString(obj, ToStringStyle.MULTI_LINE_STYLE);
        return JSON.toJSONString(obj, true);
    }

    /**
     * 对象转JSONString以便在日志中输入
     *
     * @param obj
     * @return
     */
    public static String obj2JSONString(Object obj) {
        return ReflectionToStringBuilder.toString(obj, ToStringStyle.JSON_STYLE);
    }
}