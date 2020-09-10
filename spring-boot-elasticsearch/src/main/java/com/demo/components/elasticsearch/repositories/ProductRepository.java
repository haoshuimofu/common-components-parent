package com.demo.components.elasticsearch.repositories;

import com.demo.components.elasticsearch.base.repository.AbstractElasticsearchRepository;
import com.demo.components.elasticsearch.model.Product;
import org.elasticsearch.search.SearchHit;
import org.springframework.stereotype.Repository;

/**
 * @author wude
 * @date 2020/2/15 18:55
 */
@Repository
public class ProductRepository extends AbstractElasticsearchRepository<Product> {
    @Override
    public Product convert(SearchHit hit) {
        return null;
    }

    //    @Value("${elasticsearch.index.product}")
//    private String index;
//    @Value("${elasticsearch.index.product.max.boost:1}")
//    private int max_word_boost;
//    @Value("${elasticsearch.index.product.smart.boost:5}")
//    private int smart_boost;
//    @Value("${elasticsearch.index.product.mix-analyzer:true}")
//    private boolean isMixAnalyzer;
//    @Value("${elasticsearch.index.product.smart.synonym.boost:0.2f}")
//    private float smart_synonym_boost;

}