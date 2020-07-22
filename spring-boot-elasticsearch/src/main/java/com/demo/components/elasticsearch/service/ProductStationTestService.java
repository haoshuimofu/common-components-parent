package com.demo.components.elasticsearch.service;

import com.alibaba.fastjson.JSONObject;
import com.demo.components.elasticsearch.config.ElasticsearchRestClient;
import com.demo.components.elasticsearch.config.ElasticsearchRestProperties;
import com.demo.components.elasticsearch.model.Product;
import com.demo.components.elasticsearch.model.StationProduct;
import com.demo.components.elasticsearch.repositories.ProductRepository;
import com.demo.components.elasticsearch.repositories.ProductStationRepository;
import com.demo.components.elasticsearch.repositories.StationProductReplicaRepository;
import com.demo.components.elasticsearch.repositories.StationProductRepository;
import com.demo.components.elasticsearch.utils.CollectionUtils;
import com.demo.components.elasticsearch.utils.DateTimeUtils;
import com.demo.components.elasticsearch.utils.ListUtils;
import org.elasticsearch.action.admin.indices.flush.FlushRequest;
import org.elasticsearch.action.admin.indices.flush.FlushResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.bytes.BytesArray;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.ToXContent;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.ReindexRequest;
import org.elasticsearch.index.reindex.RemoteInfo;
import org.elasticsearch.rest.RestStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author wude
 * @date 2020/2/15 19:10
 */
@Service
public class ProductStationTestService implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(ProductStationTestService.class);

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductStationRepository productStationRepository;
    @Autowired
    private StationProductRepository stationProductRepository;
    @Autowired
    private StationProductReplicaRepository stationProductReplicaRepository;

    /**
     * _reindex测试，响应结果参考: es-response/BulkByScrollResponse.json
     *
     * @return
     * @throws Exception
     */
    public BulkByScrollResponse reindex() throws Exception {
        // 执行成功后针对destination index flush一次
        String remoteSchema = "http";
        String remoteAddress = "127.0.0.1:9800";
        ElasticsearchRestProperties remoteProperties = new ElasticsearchRestProperties();
        remoteProperties.setSchema(remoteSchema);
        remoteProperties.setServers(remoteAddress);
        ElasticsearchRestClient remoteRestClient = new ElasticsearchRestClient(remoteProperties);

        // 先判断source index在destination是否存在，如果不存在，根据source index创建索引
        String sourceIndex = stationProductRepository.getIndex();
        String destinationIndex = "destination_index";
        boolean isIndexExistsOnDestination = remoteRestClient.getRestClient()
                .indices().exists(new GetIndexRequest(destinationIndex), RequestOptions.DEFAULT);
        if (!isIndexExistsOnDestination) {
            // 本来想通过source index直接创建destination index, 但是getSetting getMappings不太方面
            // 假设知道索引结构(settings和mappings)
            String indexSchema = "{\n" +
                    "  \"settings\": {\n" +
                    "    \"number_of_shards\": \"10\",\n" +
                    "    \"number_of_replicas\": \"1\",\n" +
                    "    \"refresh_interval\": \"5s\",\n" +
                    "    \"similarity\": {\n" +
                    "      \"similarity_halfh\": {\n" +
                    "        \"type\": \"BM25\",\n" +
                    "        \"k1\": 1.2,\n" +
                    "        \"b\": 0.2\n" +
                    "      }\n" +
                    "    }\n" +
                    "  },\n" +
                    "  \"mappings\": {\n" +
                    "    \"properties\": {\n" +
                    "      \"name\": {\n" +
                    "        \"type\": \"keyword\",\n" +
                    "        \"index\": true,\n" +
                    "        \"store\": false\n" +
                    "      },\n" +
                    "      \"name_analyze\": {\n" +
                    "        \"type\": \"text\",\n" +
                    "        \"index\": true,\n" +
                    "        \"store\": false,\n" +
                    "        \"analyzer\": \"whitespace\",\n" +
                    "        \"search_analyzer\": \"whitespace\",\n" +
                    "        \"similarity\": \"similarity_halfh\",\n" +
                    "        \"copy_to\": \"description\"\n" +
                    "      },\n" +
                    "      \"base_name\": {\n" +
                    "        \"type\": \"keyword\",\n" +
                    "        \"index\": true,\n" +
                    "        \"store\": false\n" +
                    "      },\n" +
                    "      \"search_tags\": {\n" +
                    "        \"type\": \"text\",\n" +
                    "        \"analyzer\": \"whitespace\",\n" +
                    "        \"search_analyzer\": \"whitespace\",\n" +
                    "        \"similarity\": \"similarity_halfh\",\n" +
                    "        \"index\": true,\n" +
                    "        \"store\": false\n" +
                    "      },\n" +
                    "      \"search_tags_analyzer\": {\n" +
                    "        \"type\": \"text\",\n" +
                    "        \"index\": true,\n" +
                    "        \"store\": false,\n" +
                    "        \"analyzer\": \"whitespace\",\n" +
                    "        \"search_analyzer\": \"whitespace\",\n" +
                    "        \"similarity\": \"similarity_halfh\",\n" +
                    "        \"copy_to\": \"description\"\n" +
                    "      },\n" +
                    "      \"update_time\": {\n" +
                    "        \"type\": \"long\",\n" +
                    "        \"index\": true\n" +
                    "      },\n" +
                    "      \"category\": {\n" +
                    "        \"type\": \"keyword\",\n" +
                    "        \"index\": true,\n" +
                    "        \"store\": false\n" +
                    "      },\n" +
                    "      \"category_path\": {\n" +
                    "        \"type\": \"keyword\",\n" +
                    "        \"index\": true,\n" +
                    "        \"store\": false\n" +
                    "      },\n" +
                    "      \"is_delete\": {\n" +
                    "        \"type\": \"keyword\",\n" +
                    "        \"index\": true\n" +
                    "      },\n" +
                    "      \"property_search\": {\n" +
                    "        \"type\": \"keyword\",\n" +
                    "        \"index\": true,\n" +
                    "        \"store\": false\n" +
                    "      },\n" +
                    "      \"total_sales\": {\n" +
                    "        \"type\": \"integer\",\n" +
                    "        \"index\": true\n" +
                    "      },\n" +
                    "      \"oid\": {\n" +
                    "        \"type\": \"keyword\",\n" +
                    "        \"index\": true\n" +
                    "      },\n" +
                    "      \"manage_category\": {\n" +
                    "        \"type\": \"keyword\",\n" +
                    "        \"index\": true,\n" +
                    "        \"store\": false\n" +
                    "      },\n" +
                    "      \"manage_category_path\": {\n" +
                    "        \"type\": \"keyword\",\n" +
                    "        \"index\": true,\n" +
                    "        \"store\": false\n" +
                    "      },\n" +
                    "      \"brand_id\": {\n" +
                    "        \"type\": \"keyword\",\n" +
                    "        \"index\": true\n" +
                    "      },\n" +
                    "      \"description\": {\n" +
                    "        \"type\": \"text\",\n" +
                    "        \"index\": true,\n" +
                    "        \"store\": false,\n" +
                    "        \"analyzer\": \"whitespace\",\n" +
                    "        \"search_analyzer\": \"whitespace\",\n" +
                    "        \"similarity\": \"similarity_halfh\"\n" +
                    "      },\n" +
                    "      \"product_id\": {\n" +
                    "        \"type\": \"keyword\",\n" +
                    "        \"index\": true\n" +
                    "      },\n" +
                    "      \"station_id\": {\n" +
                    "        \"type\": \"keyword\",\n" +
                    "        \"index\": true\n" +
                    "      },\n" +
                    "      \"station_has_stock\": {\n" +
                    "        \"type\": \"keyword\",\n" +
                    "        \"index\": true\n" +
                    "      },\n" +
                    "      \"station_price\": {\n" +
                    "        \"type\": \"double\",\n" +
                    "        \"index\": true\n" +
                    "      },\n" +
                    "      \"station_status\": {\n" +
                    "        \"type\": \"keyword\",\n" +
                    "        \"index\": true\n" +
                    "      },\n" +
                    "      \"new_user\": {\n" +
                    "        \"type\": \"keyword\",\n" +
                    "        \"index\": true,\n" +
                    "        \"store\": false\n" +
                    "      },\n" +
                    "      \"new_user_version\": {\n" +
                    "        \"type\": \"keyword\",\n" +
                    "        \"index\": true,\n" +
                    "        \"store\": false\n" +
                    "      },\n" +
                    "      \"row_update_time\": {\n" +
                    "        \"format\": \"strict_date_optional_time||epoch_millis\",\n" +
                    "        \"type\": \"date\",\n" +
                    "        \"index\": true\n" +
                    "      }\n" +
                    "    }\n" +
                    "  }\n" +
                    "}";
            JSONObject indexSchemaJson = JSONObject.parseObject(indexSchema);
            CreateIndexRequest createIndexRequest = new CreateIndexRequest(destinationIndex);
            createIndexRequest.mapping(indexSchemaJson.getJSONObject("mappings"));
            if (indexSchemaJson.containsKey("settings")) {
                createIndexRequest.settings(indexSchemaJson.getJSONObject("settings"));
            }
            CreateIndexResponse createIndexResponse = remoteRestClient.getRestClient()
                    .indices().create(createIndexRequest, RequestOptions.DEFAULT);
            boolean success = createIndexResponse != null && createIndexResponse.isAcknowledged();
            if (!success) {
                logger.error("### 目标索引创建失败! index=[{}].", destinationIndex);
                return null;
            }

        }
        ReindexRequest reindexRequest = new ReindexRequest();
        // source
        reindexRequest.setSourceIndices(sourceIndex);
//        reindexRequest.setSourceQuery(QueryBuilders.matchAllQuery());
        reindexRequest.setSourceBatchSize(1000);
        // destination
        reindexRequest.setDestIndex(destinationIndex);
        //
        // String scheme, String host, int port, String pathPrefix, BytesReference query, String username, String password,
        //        Map<String, String> headers, TimeValue socketTimeout, TimeValue connectTimeout

        // "Validation Failed: 1: reindex from remote sources should use RemoteInfo's query instead of source's query;
        XContentBuilder contentBuilder = XContentFactory.jsonBuilder();
        QueryBuilders.matchAllQuery().toXContent(contentBuilder, ToXContent.EMPTY_PARAMS);

        RemoteInfo remoteInfo = new RemoteInfo(
                remoteSchema,
                "127.0.0.1",
                9201,
                null,
//                BytesReference.bytes(contentBuilder),
                new BytesArray(QueryBuilders.wrapperQuery("{\"bool\":{\"filter\":[{\"range\":{\"row_update_time\":{\"gte\":1594748170179,\"lte\":1594748171042}}}]}}").toString()),
                null,
                null,
                new HashMap<>(),
                TimeValue.timeValueMillis(3000),
                TimeValue.timeValueMillis(3000)
        );
        reindexRequest.setRemoteInfo(remoteInfo);
        BulkByScrollResponse response = remoteRestClient.getRestClient().reindex(reindexRequest, RequestOptions.DEFAULT);
        FlushRequest flushRequest = new FlushRequest(destinationIndex);
        FlushResponse flushResponse = remoteRestClient.getRestClient().indices().flush(flushRequest, RequestOptions.DEFAULT);
        if (flushResponse == null || flushResponse.getStatus() != RestStatus.OK) {
            logger.error("### 目标索引flush失败! index=[{}].", destinationIndex);
        }

        // 判断执行成功
        boolean success = !response.isTimedOut()
                && CollectionUtils.isEmpty(response.getSearchFailures())
                && CollectionUtils.isEmpty(response.getBulkFailures())
                && response.getStatus() != null
                && response.getTotal() == response.getStatus().getSuccessfullyProcessed();

        logger.error("### reindex执行成功? {}", success ? "Y" : "N");
        return response;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        long startTime = System.currentTimeMillis();
//        initIndex();
        long endTime = System.currentTimeMillis();
        System.err.println("### 总耗时: " + (endTime - startTime));
    }

    private void initIndex() throws Exception {
        int count = 0;
        InputStream is = this.getClass().getResourceAsStream("/data.json");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        Product product = null;
        List<StationProduct> stationProducts = new ArrayList<>();
        while (true) {
            if (count >= 100000)
                break;
            line = br.readLine();
            if (line != null) {
                if (!line.startsWith("{")) {
                    if (product != null && stationProducts.size() > 0) {
                        product.setId(stationProducts.get(0).getProductId());
                        product.setProductId(product.getId());
                        setManageCategory(product);
                        product.setRowUpdateTime(DateTimeUtils.currentTimeMillis());
                        productRepository.save(product);
                        stationProductRepository.bulkSave(stationProducts);
                        Thread.sleep(10);
                        stationProducts.clear();
                    }
                    int index = line.indexOf("-");
                    product = JSONObject.parseObject(line.substring(index + 1), Product.class);
                } else {
                    StationProduct stationProduct = JSONObject.parseObject(line, StationProduct.class);
                    stationProduct.set_id(stationProduct.getProductId() + "#" + stationProduct.getStationId());
                    stationProduct.set_routing(stationProduct.getStationId());
                    stationProduct.setName(product.getName());
                    stationProduct.setNameAnalyze(product.getNameAnalyze());
                    stationProduct.setBaseName(product.getBaseName());
                    stationProduct.setBrandId(product.getBrandId());
                    stationProduct.setCategory(product.getCategory());
                    stationProduct.setCategoryPath(product.getCategoryPath());
                    stationProduct.setIsDelete(product.getIsDelete());
                    stationProduct.setOid(product.getOid());
                    stationProduct.setManageCategoryPath(ListUtils.removeNullElements(product.getManageCategoryPath()));
                    if (CollectionUtils.isNotEmpty(stationProduct.getManageCategoryPath())) {
                        stationProduct.setManageCategory(stationProduct.getManageCategoryPath().get(stationProduct.getManageCategoryPath().size() - 1));
                    }
                    stationProduct.setPropertySearch(product.getPropertySearch());
                    stationProduct.setSearchTags(product.getSearchTags());
                    stationProduct.setSearchTagsAnalyzer(product.getSearchTagsAnalyzer());
                    stationProduct.setTotalSales(product.getTotalSales());
                    stationProduct.setUpdateTime(product.getUpdateTime());
                    stationProduct.setRowUpdateTime(DateTimeUtils.currentTimeMillis());
                    stationProducts.add(stationProduct);
                    count++;
                }
            } else {
                if (product != null && stationProducts.size() > 0) {
                    product.setId(stationProducts.get(0).getProductId());
                    product.setProductId(product.getId());
                    setManageCategory(product);
                    product.setRowUpdateTime(DateTimeUtils.currentTimeMillis());
                    productRepository.save(product);
                    stationProductRepository.bulkSave(stationProducts);
                }
                break;
            }
        }
    }

    private void setManageCategory(Product product) {
        List<Integer> categoryIds = ListUtils.removeNullElements(product.getManageCategoryPath());
        product.setManageCategoryPath(categoryIds);
        if (CollectionUtils.isNotEmpty(categoryIds)) {
            product.setManageCategory(categoryIds.get(categoryIds.size() - 1));
        }
    }
}