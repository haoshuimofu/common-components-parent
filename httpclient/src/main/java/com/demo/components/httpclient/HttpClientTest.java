package com.demo.components.httpclient;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wude
 * @date 2020/8/26 11:11
 */
public class HttpClientTest {

    // http://msgsrv-service.te.test.srv.mc.dd

    public static void main(String[] args) throws Exception {

        String msgServiceUrl = "http://msgsrv-service.te.test.srv.mc.dd/api/sms/notice/msgCode";
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("mobile", "17717929937");
        paramMap.put("content", "9937");

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-type", "application/x-www-form-urlencoded; charset=utf-8");
        headerMap.put("Content-type", "application/json; charset=utf-8");
        headerMap.put("Accept", "application/json");
        headerMap.put("X-APP", "app");

        long start = System.currentTimeMillis();
//        String data = HttpClientUtils.post(msgServiceUrl, headerMap, paramMap, 1000, 2000, "utf-8");
        String data = HttpClientUtils.post("http://localhost:18081/test/stringEntity",
                headerMap, JSON.toJSONString(paramMap), 1000, 2000, null);

//        String data = HttpClientUtils.get("http://config-service.te.test.srv.mc.dd/config/station?" +
//                "stationId=551cb07c916edf65098d4c3f&type=custom_telephone&key=custom_telephone", null, 100, 100);

        long end = System.currentTimeMillis();
        System.err.println((end - start) + " ~~~ " + data);
    }
}