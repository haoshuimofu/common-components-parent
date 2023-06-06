package com.demo.components.elasticsearch.repositories;

import com.alibaba.fastjson.JSON;
import com.demo.components.elasticsearch.PageResult;
import com.demo.components.elasticsearch.base.repository.AbstractElasticsearchRepository;
import com.demo.components.elasticsearch.model.Demo1;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Requests;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wude
 * @date 2020/2/15 18:56
 */
@Repository
public class DemoRepository1 extends AbstractElasticsearchRepository<Demo1> {

    private static final Logger logger = LoggerFactory.getLogger(DemoRepository1.class);


    public List<Demo1> searchByKeyword(String keyword, int from, int size) {
        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termQuery("is_delete", 0))
                .must(QueryBuilders.matchQuery("description", keyword));
        SearchSourceBuilder searchSourceBuilder = SearchSourceBuilder.searchSource()
                .query(queryBuilder)
                .from(from).size(size)
                .fetchSource(new String[]{"name", "title"}, null);
        SearchRequest searchRequest = Requests.searchRequest(getIndex())
                .source(searchSourceBuilder)
                .searchType(SearchType.DFS_QUERY_THEN_FETCH);
        List<Demo1> demoList = new ArrayList<>();
        try {
            SearchResponse searchResponse = getClient().search(searchRequest, RequestOptions.DEFAULT);
            if (searchResponse.getHits().getTotalHits().value > 0) {
                for (SearchHit hit : searchResponse.getHits().getHits()) {
                    Demo1 demo = new Demo1();
                    demo.setId(hit.getId());
                    demo.setName((String) hit.getSourceAsMap().get("name"));
                    demo.setTitle((String) hit.getSourceAsMap().get("title"));
                    demoList.add(demo);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return demoList;
    }


    public PageResult<Demo1> page(int from, int size) throws Exception {
        QueryBuilder queryBuilder = QueryBuilders.boolQuery().filter(QueryBuilders.termQuery("status", 1));
        return search(queryBuilder, null, from, size, null, null, 0);
    }


    @Override
    public Demo1 convert(String id, Map<String, Object> sourceAsMap, String sourceAsString) {
        Demo1 demo = JSON.parseObject(sourceAsString, Demo1.class);
        demo.set_id(id);
        return demo;
    }

    @Override
    public String getEnv() {
        return "default";
    }
}