package com.demo.components.elasticsearch.repositories;

import com.demo.components.elasticsearch.base.repository.AbstractElasticsearchRepository;
import com.demo.components.elasticsearch.model.Product;

import java.util.Map;

/**
 * @author wude
 * @date 2020/2/15 18:55
 */
//@Repository
public class ProductRepository extends AbstractElasticsearchRepository<Product> {

    @Override
    public Product convert(String id, Map<String, Object> sourceAsMap, String sourceAsString) {
        return null;
    }

    @Override
    public String getEnv() {
        return "default";
    }
}