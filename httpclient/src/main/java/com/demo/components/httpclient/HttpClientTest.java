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

        String charset = "UTF-8";

        String msgServiceUrl = "http://msg1srv-service.te.test.srv/api/sms/notice/msgCode";
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("mobile", "17717929937");
        paramMap.put("content", "9937");

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-type", "application/json; charset=utf-8");
        headerMap.put("Accept", "application/json");
        headerMap.put("X-APP", "app");


        // POST提交 Params最终是以(json)String提价, 接口可以以 @RequestBody String/Ojbect接收
        String data = HttpClientUtils.post("http://localhost:8080/http/post/stringEntity",
                headerMap, JSON.toJSONString(paramMap), 1000, 60000, charset);
        System.err.println("post StringEntity: " + data);

        data = HttpClientUtils.post("http://localhost:8080/http/post/stringEntity1",
                headerMap, JSON.toJSONString(paramMap), 1000, 60000, charset);
        System.err.println("post StringEntity: " + data);

        // POST FORM提交
        headerMap.put("Content-type", "application/x-www-form-urlencoded; charset=utf-8"); // 表单默认content-type

        data = HttpClientUtils.post("http://localhost:8080/http/post/form",
                headerMap, JSON.toJSONString(paramMap), 1000, 60000, charset);
        System.err.println("post form: " + data);

        headerMap.put("Content-type", "application/form-data; charset=utf-8"); // 表单 content-type = form-data

        data = HttpClientUtils.post("http://localhost:8080/http/post/form",
                headerMap, JSON.toJSONString(paramMap), 1000, 60000, charset);
        System.err.println("post form data: " + data);

        headerMap.put("Content-type", "application/x-www-form-urlencoded; charset=utf-8");

        // form-urlencoded不可以, form-data可以提交成功
        try {
            data = HttpClientUtils.post("http://localhost:8080/http/post/stringEntity",
                    headerMap, JSON.toJSONString(paramMap), 1000, 60000, charset);
            System.err.println("post form to StringEntity : " + data);
        } catch (Exception e) {
            System.err.println("post form to StringEntity error!");
        }

        try {
            data = HttpClientUtils.post("http://localhost:8080/http/post/stringEntity1",
                    headerMap, JSON.toJSONString(paramMap), 1000, 60000, charset);
            System.err.println("post form StringEntity: " + data);
        } catch (Exception e) {
            System.err.println("post form to StringEntity1 error!");
        }
    }
}