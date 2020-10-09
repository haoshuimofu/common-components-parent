package com.demo.components.elasticsearch.controller;

import com.demo.components.elasticsearch.JsonResult;
import com.demo.components.elasticsearch.repositories.StationProductRepository;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author wude
 * @date 2020/10/9 14:43
 */
@RestController
@RequestMapping("/station/product/")
public class StationProductController {

    @Autowired
    private StationProductRepository stationProductRepository;

    @RequestMapping("count")
    public JsonResult<Long> countAll() throws IOException {
        CountRequest request = new CountRequest();
        request.query(QueryBuilders.matchAllQuery());
        return JsonResult.success(stationProductRepository.count(request));
    }

}