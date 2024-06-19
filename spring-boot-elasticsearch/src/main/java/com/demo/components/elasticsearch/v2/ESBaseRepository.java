package com.demo.components.elasticsearch.v2;

import com.alibaba.fastjson.JSON;
import com.demo.components.elasticsearch.config.ESRestClientContainer;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Optional;

/**
 * @author dewu.de
 * @date 2024-06-07 4:33 下午
 */
@Slf4j
public abstract class ESBaseRepository<T extends BaseIndex> implements InitializingBean {

    @Autowired
    private ESRestClientContainer esRestClientContainer;

    protected RestHighLevelClient getClient() {
        return esRestClientContainer.restHighLevelClient("default");
    }

    public IndexRequest buildIndexRequest(MetaIndex<T> doc) {
        IndexRequest indexRequest = new IndexRequest(doc.getIndex());
        indexRequest.id(doc.getDoc().getId());
        Optional.ofNullable(doc.getDoc().getRouting()).ifPresent(indexRequest::routing);
        indexRequest.source(JSON.toJSONBytes(doc.getDoc()), XContentType.JSON);
        return indexRequest;
    }

    public boolean bulkIndex(BulkRequest bulkRequest) {
        try {
            BulkResponse bulkResponse = getClient().bulk(bulkRequest, RequestOptions.DEFAULT);
            if (bulkResponse.hasFailures()) {
                log.error("[ES] bulk index fail! response={}", JSON.toJSONString(bulkResponse));
            }
            return !bulkResponse.hasFailures();
        } catch (IOException e) {
            log.error("[ES] bulk index error!", e);
            return false;
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

}
