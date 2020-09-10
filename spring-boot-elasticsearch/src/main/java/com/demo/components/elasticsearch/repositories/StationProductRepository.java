package com.demo.components.elasticsearch.repositories;

import com.demo.components.elasticsearch.base.repository.AbstractElasticsearchRepository;
import com.demo.components.elasticsearch.model.StationProduct;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

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
    public StationProduct convert(SearchHit hit) {
        return null;
    }

    //    @Value("${elasticsearch.index.station_product}")
//    private String index;
//    @Value("${elasticsearch.index.station_product.max.boost:1}")
//    private int max_word_boost;
//    @Value("${elasticsearch.index.station_product.smart.boost:5}")
//    private int smart_boost;
//    @Value("${elasticsearch.index.station_product.mix-analyzer:true}")
//    private boolean isMixAnalyzer;
//    @Value("${elasticsearch.index.station_product.smart.synonym.boost:0.2f}")
//    private float smart_synonym_boost;
//    @Value("${elasticsearch.keyword.category.mapping.boost:2}")
//    private float keyword_category_boost;
//    @Value("${elasticsearch.keyword.category.mapping.top:1}")
//    private int keyword_category_top;
//    @Value("${elasticsearch.keyword.boost.value:2}")
//    private int keywordBoostValue;
//    @Value("${elasticsearch.keyword.boost.factor.op-type:multiply}")
//    private String keywordBoostFactorOpType;

}