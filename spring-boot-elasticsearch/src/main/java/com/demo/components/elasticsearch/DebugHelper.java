package com.demo.components.elasticsearch;

import com.alibaba.fastjson.JSON;
import com.demo.components.elasticsearch.utils.LogUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Debug工具
 *
 * @Author wude
 * @Create 2019-08-20 15:27
 */
@Component
public class DebugHelper {

    @Value("${elasticsearch.query.debug.enable:true}")
    private boolean esQueryDebug;

    /**
     * debug ES查询条件和结果
     *
     * @param requestBuilder
     * @param response
     * @param logger
     */
    public void debugESQuery(SearchSourceBuilder requestBuilder, SearchResponse response, Logger logger) {
        if (esQueryDebug) {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            logger.info("### [{}]~Elasticsearch查询请求: {}",
                    uuid, LogUtils.obj2PrettyString(JSON.parse(requestBuilder.toString())));
            if (response != null) {
                logger.info("### [{}]~Elasticsearch查询结果: {}",
                        uuid, LogUtils.obj2PrettyString(response));
            }
        }
    }

}