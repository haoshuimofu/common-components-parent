package com.demo.components.elasticsearch.utils;

import com.alibaba.fastjson.JSON;

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
        return JSON.toJSONString(obj, true);
    }
}