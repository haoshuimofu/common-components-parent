package com.demo.components.elasticsearch.repositories;

import com.demo.components.elasticsearch.base.repository.AbstractElasticsearchRepository;
import com.demo.components.elasticsearch.model.StationProduct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * 服务站商品
 *
 * @author wude
 * @date 2020/2/27 17:33
 */
@Repository
public class StationProductRepository extends AbstractElasticsearchRepository<StationProduct> {

    private static final Logger logger = LoggerFactory.getLogger(StationProductRepository.class);

    @Override
    public StationProduct convert(String id, Map<String, Object> sourceAsMap, String sourceAsString) {
        return null;
    }

}