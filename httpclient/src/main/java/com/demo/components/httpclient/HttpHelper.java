package com.demo.components.httpclient;

import org.apache.http.client.methods.HttpRequestBase;

import java.util.Map;

/**
 * HTTP辅助工具类
 *
 * @author wude
 * @date 2020/8/27 11:18
 */
public class HttpHelper {

    /**
     * 为HTTP Method实例设置Header
     *
     * @param httpRequestBase
     * @param headers
     */
    public static void addHeaders(HttpRequestBase httpRequestBase, Map<String, String> headers) {
        if (httpRequestBase != null && headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> headerEntry : headers.entrySet()) {
                httpRequestBase.addHeader(headerEntry.getKey(), headerEntry.getValue());
            }
        }
    }

}