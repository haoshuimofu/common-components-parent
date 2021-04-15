package com.demo.components.elasticsearch.repositories;

import com.demo.components.elasticsearch.base.repository.AbstractElasticsearchRepository;
import com.demo.components.elasticsearch.model.StationProductReplica;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 服务站商品
 *
 * @author wude
 * @date 2020/2/27 17:33
 */
//@Repository
public class StationProductReplicaRepository extends AbstractElasticsearchRepository<StationProductReplica> {

    private static final Logger logger = LoggerFactory.getLogger(StationProductReplicaRepository.class);


    @Override
    public StationProductReplica convert(String id, Map<String, Object> sourceAsMap, String sourceAsString) {
        return null;
    }

    @Override
    public String getEnv() {
        return "default";
    }
}