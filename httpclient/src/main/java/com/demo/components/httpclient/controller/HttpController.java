package com.demo.components.httpclient.controller;

import com.alibaba.fastjson.JSON;
import com.demo.components.httpclient.JsonResult;
import com.demo.components.httpclient.request.SmsRequestVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * @author wude
 * @date 2020/8/27 12:36
 */
@RestController
@RequestMapping("/http")
public class HttpController {

    private static Logger LOGGER = LoggerFactory.getLogger(HttpController.class);

    @PostMapping("/post/stringEntity")
    public JsonResult<String> stringEntity(@RequestBody String requestBody) {
        LOGGER.info("/http/post/stringEntity requestBody String is: {}", requestBody);
        SmsRequestVO requestVO = JSON.parseObject(requestBody, SmsRequestVO.class);
        return JsonResult.success(requestVO.toString());
    }

    @PostMapping("/post/stringEntity1")
    public JsonResult<String> stringEntity(@RequestBody SmsRequestVO requestBody) {
        LOGGER.info("/http/post/stringEntity requestBody Object is: {}", requestBody);
        return JsonResult.success(requestBody.toString());
    }

    @PostMapping("/post/form")
    public JsonResult<String> postForm(@ModelAttribute SmsRequestVO requestBody) {
        LOGGER.info("/http/post/form request is: {}", requestBody.toString());
        return JsonResult.success(requestBody.toString());
    }

}