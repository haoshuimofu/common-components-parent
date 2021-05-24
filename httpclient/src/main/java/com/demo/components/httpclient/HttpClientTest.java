package com.demo.components.httpclient;

import com.alibaba.fastjson.JSON;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wude
 * @date 2020/8/26 11:11
 */
public class HttpClientTest {

    // http://msgsrv-service.te.test.srv.mc.dd

    public static void main(String[] args) throws Exception {

        String msgServiceUrl = "https://openapi-fxg.jinritemai.com/product/detail";
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("app_key", "6959388742876399134");
        paramMap.put("method", "product.detail");
        paramMap.put("access_token", "3c90fd8e-e739-4e38-9523-6873b4c3a300");
        paramMap.put("param_json", "{\"product_id\":\"3475079938988766123\"}");
        paramMap.put("timestamp", "2021-05-12 16:39:10");
        paramMap.put("v", "2");
        paramMap.put("sign", "a9f839265394c255c616e8f2449cdec4");

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-type", "application/json; charset=utf-8");
        headerMap.put("Accept", "application/json");

        StringBuffer sb = new StringBuffer(msgServiceUrl).append("?");

        int paramSize = paramMap.size();
        int index = 0;
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            sb.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            if (index != paramSize -1) {
                sb.append("&");
            }
        }
        System.err.println(HttpClientUtils.get(sb.toString(), headerMap, 1000, 1000));


//        // POST提交 Params最终是以(json)String提价, 接口可以以 @RequestBody String/Ojbect接收
//        String data = HttpClientUtils.post("http://localhost:8080/http/post/stringEntity",
//                headerMap, JSON.toJSONString(paramMap), 1000, 60000, charset);
//        System.err.println("post StringEntity: " + data);
//
//        data = HttpClientUtils.post("http://localhost:8080/http/post/stringEntity1",
//                headerMap, JSON.toJSONString(paramMap), 1000, 60000, charset);
//        System.err.println("post StringEntity: " + data);
//
//        // POST FORM提交
//        headerMap.put("Content-type", "application/x-www-form-urlencoded; charset=utf-8"); // 表单默认content-type
//
//        data = HttpClientUtils.post("http://localhost:8080/http/post/form",
//                headerMap, JSON.toJSONString(paramMap), 1000, 60000, charset);
//        System.err.println("post form: " + data);
//
//        headerMap.put("Content-type", "application/form-data; charset=utf-8"); // 表单 content-type = form-data
//
//        data = HttpClientUtils.post("http://localhost:8080/http/post/form",
//                headerMap, JSON.toJSONString(paramMap), 1000, 60000, charset);
//        System.err.println("post form data: " + data);
//
//        headerMap.put("Content-type", "application/x-www-form-urlencoded; charset=utf-8");
//
//        // form-urlencoded不可以, form-data可以提交成功
//        try {
//            data = HttpClientUtils.post("http://localhost:8080/http/post/stringEntity",
//                    headerMap, JSON.toJSONString(paramMap), 1000, 60000, charset);
//            System.err.println("post form to StringEntity : " + data);
//        } catch (Exception e) {
//            System.err.println("post form to StringEntity error!");
//        }
//
//        try {
//            data = HttpClientUtils.post("http://localhost:8080/http/post/stringEntity1",
//                    headerMap, JSON.toJSONString(paramMap), 1000, 60000, charset);
//            System.err.println("post form StringEntity: " + data);
//        } catch (Exception e) {
//            System.err.println("post form to StringEntity1 error!");
//        }

    }
}