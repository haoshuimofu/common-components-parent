package com.demo.components.elasticsearch.controller;

import com.demo.components.elasticsearch.JsonResult;
import com.demo.components.elasticsearch.service.ProductStationTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Elasticsearch API测试控制器
 *
 * @author wude
 * @date 2020/6/19 13:45
 */
@RestController
@RequestMapping("/es/api")
public class ESApiTestController {

    @Autowired
    private ProductStationTestService productStationTestService;

    @GetMapping("reindex")
    public JsonResult reindex() throws Exception {
        return JsonResult.success(productStationTestService.reindex());
    }

}