package com.demo.components.httpclient.pooling.config;

import com.alibaba.fastjson.JSON;
import com.demo.components.httpclient.HttpHelper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wude
 * @date 2020/9/2 10:04
 */
@Component
public class PoolingHttpClient {

    @Autowired
    @Qualifier("customHttpClientProperties")
    private HttpClientPoolingProperties properties;

    @Autowired
    @Qualifier("customConnectionManager")
    private PoolingHttpClientConnectionManager connectionManager;


    public String postForm() throws Exception {
        String charset = "UTF-8";

        String msgServiceUrl = "http://msg1srv-service.te.test.srv/api/sms/notice/msgCode";
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("mobile", "17717929937");
        paramMap.put("content", "9937");

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-type", "application/json; charset=utf-8");
        headerMap.put("Accept", "application/json");
        headerMap.put("X-APP", "app");

        headerMap.put("Content-type", "application/form-data; charset=utf-8"); // 表单 content-type = form-data

        String data = HttpClientPoolingUtils.post("http://localhost:8080/http/post/form",
                headerMap, JSON.toJSONString(paramMap), 1000, 60000, charset);
        System.err.println("post form data: " + data);


        CloseableHttpClient httpClient = HttpHelper.get(connectionManager);
        RequestConfig requestConfig = HttpHelper.requestConfig(properties, 3000);


        return "success";
    }

}