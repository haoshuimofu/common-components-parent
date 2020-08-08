package com.demo.components.elasticsearch.repositories;

import com.demo.components.elasticsearch.base.repository.AbstractElasticsearchRepository;
import com.demo.components.elasticsearch.model.ProductStation;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * 服务站商品
 *
 * @author wude
 * @date 2020/2/27 17:33
 */
@Repository
public class ProductStationRepository extends AbstractElasticsearchRepository<ProductStation> {
    @Override
    public ProductStation convert(String id, Map<String, Object> sourceMap) {
        return null;
    }

//    @Value("${elasticsearch.index.product_station}")
//    private String index;


}