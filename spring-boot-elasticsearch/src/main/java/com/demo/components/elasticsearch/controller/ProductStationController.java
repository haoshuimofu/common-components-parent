package com.demo.components.elasticsearch.controller;

import com.demo.components.elasticsearch.service.ddmc.ProductStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wude
 * @date 2020/2/15 21:21
 */
@RestController
@RequestMapping("ddmc/product")
public class ProductStationController {

    @Autowired
    private ProductStationService productStationService;

//    @PostMapping("searchProductByKeyword")
//    public JsonResult searchProductByKeyword(@RequestBody SearchByKeywordRequestVo requestVo) throws Exception {
//        return JsonResult.success(productStationService.searchProductByKeyword(requestVo.getStationId(), requestVo.getKeyword(),
//                requestVo.getSkip(), requestVo.getLimit()));
//    }
//
//    @GetMapping("getById")
//    public JsonResult getById(@RequestParam("id") String id) throws Exception {
//        return JsonResult.success(productStationService.getProductById(id));
//    }

}