package com.demo.components.stream.rabbit.message.pollable;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Update By Query API | Java REST Client [7.6] | Elastic
 * https://www.elastic.co/guide/en/elasticsearch/client/java-rest/7.6/java-rest-high-document-update-by-query.html
 */
@Component
public class SyncProduct2StationConsumer {

    private static final Logger logger = LoggerFactory.getLogger(SyncProduct2StationConsumer.class);


    public void consume(SyncProduct2StationMessage message) {
        System.err.println(JSON.toJSONString(message));
    }


}
