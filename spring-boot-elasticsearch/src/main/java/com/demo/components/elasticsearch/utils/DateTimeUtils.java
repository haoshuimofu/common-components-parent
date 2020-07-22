package com.demo.components.elasticsearch.utils;

import java.util.Date;

/**
 * 日期时间工具类
 *
 * @author wude
 * @date 2020/7/14 11:09
 */
public class DateTimeUtils {

    /**
     * 返回系统时间毫秒数
     *
     * @return
     */
    public static long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 返回系统时间毫秒数
     *
     * @return
     */
    public static long currentTimeMillis(Date date) {
        return date == null ? 0 : date.getTime();
    }

}