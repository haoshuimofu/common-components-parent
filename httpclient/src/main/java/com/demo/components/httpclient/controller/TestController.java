package com.demo.components.httpclient.controller;

import com.alibaba.fastjson.JSON;
import com.demo.components.httpclient.JsonResult;
import com.demo.components.httpclient.request.SmsRequestVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wude
 * @date 2020/8/27 12:36
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping("stringEntity")
    public JsonResult<String> stringEntity(@RequestBody String requestBody) {
        SmsRequestVO requestVO = JSON.parseObject(requestBody, SmsRequestVO.class);
        return JsonResult.success(requestVO.toString());
    }

    @PostMapping("stringEntity1")
    public JsonResult<String> stringEntity(@RequestBody SmsRequestVO requestBody) {
        return JsonResult.success(requestBody.toString());
    }

}