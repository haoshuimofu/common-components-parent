package com.demo.components.httpclient.controller;

import com.alibaba.fastjson.JSON;
import com.demo.components.httpclient.JsonResult;
import com.demo.components.httpclient.pooling.config.HttpClientPoolingProperties;
import com.demo.components.httpclient.pooling.config.HttpClientPoolingUtils;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wude
 * @date 2020/10/13 10:36
 */
@RestController
@RequestMapping("/request/test")
public class RequestTestController {


    @Autowired
    @Qualifier("customHttpClientProperties")
    private HttpClientPoolingProperties properties;

    @Autowired
    @Qualifier("customConnectionManager")
    private PoolingHttpClientConnectionManager connectionManager;


    @RequestMapping("do")
    public JsonResult requestTest() {
        Map<String, String> params = new HashMap<>();
        params.put("mobile", "17717929937");
        params.put("content", "短信测试内容!");
        HttpClientPoolingUtils poolingUtils = new HttpClientPoolingUtils(connectionManager, properties);
        String result = null;
        try {
            result = poolingUtils.post("http://localhost:8080/http/post/stringEntity",
                    null,
                    JSON.toJSONString(params),
                    1000,
                    1000,
                    "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JsonResult.success(result);
    }
}