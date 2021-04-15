package com.demo.components.elasticsearch.base.repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.demo.components.elasticsearch.DebugHelper;
import com.demo.components.elasticsearch.Pagation;
import com.demo.components.elasticsearch.annotation.*;
import com.demo.components.elasticsearch.base.model.BaseIndexModel;
import com.demo.components.elasticsearch.config.ElasticsearchConfig;
import com.demo.components.elasticsearch.config.ElasticsearchRestDynamicConfig;
import com.demo.components.elasticsearch.request.SearchOptions;
import com.demo.components.elasticsearch.utils.StringUtils;
import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.elasticsearch.client.RequestOptions.DEFAULT;

/**
 * ES抽象Repository类，业务类Repo继承它即可
 *
 * @author wude
 * @date 2019-07-23 19:03
 */
public abstract class AbstractElasticsearchRepository<T extends BaseIndexModel> implements CrudRepository<T>, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(AbstractElasticsearchRepository.class);

    private Class<T> entityClass;
    private String index;
    private String schema;
    private boolean autoId;

    @Autowired
    private ElasticsearchConfig config;
    @Autowired
    private ElasticsearchRestDynamicConfig restDynamicConfig;
    @Autowired
    private DebugHelper debugHelper;

    /**
     * 获取集群环境标识
     *
     * @return
     */
    public abstract String getEnv();

    /**
     * 获取当前Elasticsearch环境对应的RestHighLevelClient示例
     *
     * @return
     */
    public RestHighLevelClient getClient() {
        return config.getRestClient(getEnv()).getRestClient();
    }

    public long searchTimeout() {
        return restDynamicConfig.getSearchTimeout();
    }

    public long searchTimeoutMax() {
        return restDynamicConfig.getSearchTimeoutMax();
    }


    @Override
    public String getIndex() {
        return this.index;
    }

    @Override
    public boolean isIndexExists() throws IOException {
        return getClient().indices()
                .exists(new GetIndexRequest(getIndex()), DEFAULT);
    }

    @Override
    public boolean deleteIndex() throws IOException {
        AcknowledgedResponse response = getClient().indices().
                delete(new DeleteIndexRequest(getIndex()), DEFAULT);
        return response != null && response.isAcknowledged();
    }

    @Override
    public boolean createIndex() throws IOException {
        InputStream is = null;
        if (StringUtils.isNotBlank(schema)) {
            is = this.getClass().getResourceAsStream(schema.startsWith("/") ? schema : "/" + schema);
        }
        if (is == null) {
            logger.error("### 索引创建失败! schema文件无效! index=[{}], schema=[{}].", getIndex(), schema);
            throw new RuntimeException("索引创建失败! schema文件无效!");
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        JSONObject schemaJson;
        try {
            schemaJson = JSON.parseObject(sb.toString());
        } catch (Exception e) {
            logger.error("### 索引创建失败! schema文件格式错误! index=[{}], schema=[{}].", getIndex(), schema, e);
            throw new RuntimeException("索引创建失败! schema文件内容格式错误!", e);
        }
        CreateIndexRequest request = new CreateIndexRequest(getIndex());
        if (schemaJson.containsKey("settings")) {
            request.settings(schemaJson.getJSONObject("settings"));
        }
        request.mapping(schemaJson.getJSONObject("mappings"));
        CreateIndexResponse response = getClient().indices().create(request, DEFAULT);
        return response.isAcknowledged();
    }

    @Override
    public boolean save(T model) throws Exception {
        return save(model, false);
    }

    @Override
    public boolean save(T model, boolean create) throws Exception {
        IndexRequest request = BuildingIndexUtils.buildIndexRequest(model, entityClass, create, autoId, getIndex());
        IndexResponse response = getClient().index(request, DEFAULT);
        DocWriteResponse.Result result = response.getResult();
        boolean success = create ? result.getOp() == DocWriteResponse.Result.CREATED.getOp() :
                (result.getOp() == DocWriteResponse.Result.CREATED.getOp()
                        || result.getOp() == DocWriteResponse.Result.UPDATED.getOp());
        if (success) {
            logger.info("### 保存索引记录成功! _index=[{}], _id=[{}], create=[{}], opType=[{}], result=[{}].",
                    getIndex(), request.id(), create, result.getOp(), result);
        } else {
            logger.error("### 保存索引记录失败! _index=[{}], _id=[{}], create=[{}], opType=[{}], result=[{}], response=[{}].",
                    getIndex(), request.id(), create, result.getOp(), result, response.toString());
        }
        return success;

    }

    @Override
    public void saveAsync(T model) throws Exception {
        saveAsync(model, false);
    }

    @Override
    public void saveAsync(T model, boolean create) throws Exception {
        IndexRequest request = BuildingIndexUtils.buildIndexRequest(model, entityClass, create, autoId, getIndex());
        getClient().indexAsync(request, DEFAULT, new ActionListener<IndexResponse>() {

            @Override
            public void onResponse(IndexResponse indexResponse) {
                DocWriteResponse.Result result = indexResponse.getResult();
                boolean success = create ? result.getOp() == DocWriteResponse.Result.CREATED.getOp() :
                        (result.getOp() == DocWriteResponse.Result.CREATED.getOp()
                                || result.getOp() == DocWriteResponse.Result.UPDATED.getOp());
                if (success) {
                    logger.info("### 异步保存索引记录成功! _index=[{}], _id=[{}], create=[{}], opType=[{}], result=[{}].",
                            getIndex(), request.id(), create, result.getOp(), result);
                } else {
                    logger.error("### 异步保存索引记录失败! _index=[{}], _id=[{}], create=[{}], opType=[{}], result=[{}], response=[{}].",
                            getIndex(), request.id(), create, result.getOp(), result, indexResponse.toString());
                }
            }

            @Override
            public void onFailure(Exception e) {
                logger.error("### 异步保存索引记录失败! _index=[{}], _id=[{}], create=[{}].", getIndex(), request.id(), create, e);
            }

        });
    }

    @Override
    public boolean bulkSave(List<T> models) throws Exception {
        return bulkSave(models, false);
    }

    @Override
    public boolean bulkSave(List<T> models, boolean create) throws Exception {
        BulkRequest bulkRequest = BuildingIndexUtils.bulkBuildIndexRequest(models, entityClass, create, autoId, getIndex());
        BulkResponse bulkResponse = getClient().bulk(bulkRequest, DEFAULT);
        if (bulkResponse.hasFailures()) {
            logger.error("### 批量保存索引记录失败! _index=[{}], create=[{}], bulkSize=[{}], hasFailures=[{}], failureMessage=[{}].",
                    getIndex(), create, bulkRequest.requests().size(), true, bulkResponse.buildFailureMessage());
        } else {
            logger.info("### 批量保存索引记录成功! _index=[{}], create=[{}], bulkSize=[{}], hasFailures=[{}].",
                    getIndex(), create, bulkRequest.requests().size(), false);
        }
        return !bulkResponse.hasFailures();
    }

    @Override
    public void bulkSaveAsync(List<T> models) throws Exception {
        bulkSaveAsync(models, false);
    }

    @Override
    public void bulkSaveAsync(List<T> models, boolean create) throws Exception {
        BulkRequest bulkRequest = BuildingIndexUtils.bulkBuildIndexRequest(models, entityClass, create, autoId, getIndex());
        getClient().bulkAsync(bulkRequest, DEFAULT, new ActionListener<BulkResponse>() {

            @Override
            public void onResponse(BulkResponse bulkResponse) {
                if (bulkResponse.hasFailures()) {
                    logger.error("### 批量异步保存索引记录失败! _index=[{}], create=[{}], bulkSize=[{}], hasFailures=[{}], failureMessage=[{}].",
                            getIndex(), create, bulkRequest.requests().size(), true, bulkResponse.buildFailureMessage());
                } else {
                    logger.info("### 批量异步保存索引记录成功! _index=[{}], create=[{}], bulkSize=[{}], hasFailures=[{}].",
                            getIndex(), create, bulkRequest.requests().size(), false);
                }
            }

            @Override
            public void onFailure(Exception e) {
                logger.error("### 批量异步保存索引记录失败! _index=[{}], create=[{}], bulkSize=[{}].",
                        getIndex(), create, bulkRequest.requests().size(), e);
            }

        });
    }

    @Override
    public boolean deleteById(String id) throws IOException {
        return deleteById(id, null);
    }

    @Override
    public boolean deleteById(String id, String routing) throws IOException {
        DeleteRequest request = new DeleteRequest(getIndex(), id);
        if (routing != null) {
            request.routing(routing);
        }
        DeleteResponse response = getClient().delete(request, DEFAULT);
        DocWriteResponse.Result result = response.getResult();
        boolean success = result.getOp() == DocWriteResponse.Result.NOT_FOUND.getOp()
                || result.getOp() == DocWriteResponse.Result.DELETED.getOp();
        if (success) {
            logger.info("### 删除索引记录成功! _index=[{}], _id=[{}], _routing=[{}], opType=[{}], result=[{}].",
                    getIndex(), id, routing, result.getOp(), result);
        } else {
            logger.error("### 删除索引记录失败! _index=[{}], _id=[{}], _routing=[{}], opType=[{}], result=[{}], response=[{}].",
                    getIndex(), id, routing, result.getOp(), result, response.toString());
        }
        return success;
    }

    @Override
    public boolean deleteByIds(List<String> ids) throws IOException {
        return deleteByIds(ids, (String) null);
    }

    @Override
    public boolean deleteByIds(List<String> ids, String globalRouting) throws IOException {
        BulkRequest bulkRequest = new BulkRequest(getIndex());
        ids.forEach(id -> bulkRequest.add(new DeleteRequest().id(id)));
        if (globalRouting != null) {
            bulkRequest.routing(globalRouting);
        }
        BulkResponse bulkResponse = getClient().bulk(bulkRequest, DEFAULT);
        boolean success = !bulkResponse.hasFailures();
        if (success) {
            logger.info("### 批量删除索引记录成功! _index=[{}], _routing=[{}], bulkSize=[{}], hasFailures=[{}].",
                    getIndex(), globalRouting, bulkRequest.requests().size(), bulkResponse.hasFailures());
        } else {
            logger.error("### 批量删除索引记录失败! _index=[{}], _routing=[{}], bulkSize=[{}], hasFailures=[{}], failureMessage=[{}].",
                    getIndex(), globalRouting, bulkRequest.requests().size(), bulkResponse.hasFailures(), bulkResponse.buildFailureMessage());
        }
        return success;
    }

    @Override
    public boolean deleteByIds(List<String> ids, List<String> routings) throws IOException {
        Assert.notEmpty(ids, "参数ids为空!");
        Assert.notEmpty(routings, "参数routings为空!");
        Assert.isTrue(ids.size() == routings.size(), "ids和routings长度不一致!");
        BulkRequest bulkRequest = new BulkRequest(getIndex());
        for (int i = 0; i < ids.size(); i++) {
            bulkRequest.add(new DeleteRequest().id(ids.get(i)).routing(routings.get(i)));
        }
        BulkResponse bulkResponse = getClient().bulk(bulkRequest, DEFAULT);
        boolean success = !bulkResponse.hasFailures();
        if (success) {
            logger.info("### 批量删除索引记录成功! _index=[{}], bulkSize=[{}], hasFailures=[{}].",
                    getIndex(), bulkRequest.requests().size(), bulkResponse.hasFailures());
        } else {
            logger.error("### 批量删除索引记录失败! _index=[{}], bulkSize=[{}], hasFailures=[{}], failureMessage=[{}].",
                    getIndex(), bulkRequest.requests().size(), bulkResponse.hasFailures(), bulkResponse.buildFailureMessage());
        }
        return success;
    }

    @Override
    public T getById(String id) throws IOException {
        return getById(id, null);
    }

    @Override
    public T getById(String id, String routing) throws IOException {
        GetRequest request = new GetRequest(getIndex(), id);
        if (routing != null) {
            request.routing(routing);
        }
        GetResponse response = getClient().get(request, DEFAULT);
        if (!response.isExists()) {
            return null;
        }
        return convert(response.getId(), response.getSourceAsMap(), response.getSourceAsString());
    }


    @Override
    public List<T> getByIds(List<String> ids) throws Exception {
        return getByIds(ids, null);
    }

    @Override
    public List<T> getByIds(List<String> ids, String globalRouting) throws Exception {
        return search(
                QueryBuilders.idsQuery().addIds(ids.toArray(new String[]{})),
                SearchOptions.instance()
                        .setRouting(globalRouting)
                        .setFrom(0).setSize(ids.size())
        ).getData();
    }

    @Override
    public boolean update(T model) throws Exception {
        UpdateRequest request = BuildingIndexUtils.buildUpdateRequest(model, entityClass, getIndex(), restDynamicConfig.getRetryOnConflict());
        try {
            UpdateResponse response = getClient().update(request, DEFAULT);
            DocWriteResponse.Result result = response.getResult();
            boolean success = response.getResult().getOp() == DocWriteResponse.Result.UPDATED.getOp()
                    || response.getResult().getOp() == DocWriteResponse.Result.NOOP.getOp();
            if (success) {
                logger.info("### 更新索引记录成功! _index=[{}], _id=[{}], _routing=[{}], opType=[{}], result=[{}].",
                        getIndex(), request.id(), request.routing(), result.getOp(), result);
            } else {
                logger.error("### 更新索引记录失败! _index=[{}], _id=[{}], _routing=[{}], opType=[{}], result=[{}], response=[{}].",
                        getIndex(), request.id(), request.routing(), result.getOp(), result, response.toString());
            }
            return success;
        } catch (ElasticsearchStatusException e) {
            // restful api, 当索引记录不存在时测试发现抛404 ElasticsearchStatusException
            if (e.status().getStatus() == 404) {
                logger.warn("### 更新索引记录失败! 索引记录不存在! _index=[{}], _id=[{}], _routing=[{}].", getIndex(), request.id(), request.routing());
                return false;
            }
            throw e;
        }
    }

    @Override
    public void updateAsync(T model) throws Exception {
        UpdateRequest updateRequest = BuildingIndexUtils.buildUpdateRequest(model, entityClass, getIndex(), restDynamicConfig.getRetryOnConflict());
        getClient().updateAsync(updateRequest, DEFAULT, new ActionListener<UpdateResponse>() {
            @Override
            public void onResponse(UpdateResponse updateResponse) {
                DocWriteResponse.Result result = updateResponse.getResult();
                boolean success = updateResponse.getResult().getOp() == DocWriteResponse.Result.UPDATED.getOp()
                        || updateResponse.getResult().getOp() == DocWriteResponse.Result.NOOP.getOp();
                if (success) {
                    logger.info("### 异步更新索引记录成功! _index=[{}], _id=[{}], _routing=[{}], opType=[{}], result=[{}].",
                            getIndex(), updateRequest.id(), updateRequest.routing(), result.getOp(), result);
                } else {
                    logger.error("### 异步更新索引记录失败! _index=[{}], _id=[{}], _routing=[{}], opType=[{}], result=[{}], response=[{}].",
                            getIndex(), updateRequest.id(), updateRequest.routing(), result.getOp(), result, updateResponse.toString());
                }
            }

            @Override
            public void onFailure(Exception e) {
                if (e instanceof ElasticsearchStatusException) {
                    ElasticsearchStatusException ee = (ElasticsearchStatusException) e;
                    if (ee.status().getStatus() == 404) {
                        logger.warn("### 异步更新索引记录失败! 索引记录不存在! _index=[{}], _id=[{}], _routing=[{}].",
                                getIndex(), updateRequest.id(), updateRequest.routing());
                        return;
                    }
                }
                logger.error("### 异步更新索引记录失败! _index=[{}], _id=[{}], _routing=[{}].",
                        getIndex(), updateRequest.id(), updateRequest.routing(), e);
            }
        });
    }

    @Override
    public boolean bulkUpdate(List<T> models) throws Exception {
        BulkRequest bulkRequest = BuildingIndexUtils.buildBulkUpdateRequest(models, entityClass, getIndex(), restDynamicConfig.getRetryOnConflict());
        BulkResponse bulkResponse = getClient().bulk(bulkRequest, DEFAULT);
        boolean success = !bulkResponse.hasFailures();
        if (success) {
            logger.info("### 批量更新索引记录成功! _index=[{}], bulkSize=[{}], hasFailure=[{}].",
                    getIndex(), bulkRequest.requests().size(), bulkResponse.hasFailures());
        } else {
            logger.error("### 批量更新索引记录失败! _index=[{}], bulkSize=[{}], hasFailure=[{}], failureMessage=[{}].",
                    getIndex(), bulkRequest.requests().size(), bulkResponse.hasFailures(), bulkResponse.buildFailureMessage());
        }
        return success;
    }

    @Override
    public void bulkUpdateAsync(List<T> models) throws Exception {
        BulkRequest bulkRequest = BuildingIndexUtils.buildBulkUpdateRequest(models, entityClass, getIndex(), restDynamicConfig.getRetryOnConflict());
        getClient().bulkAsync(bulkRequest, DEFAULT, new ActionListener<BulkResponse>() {
            @Override
            public void onResponse(BulkResponse bulkResponse) {
                boolean success = !bulkResponse.hasFailures();
                if (success) {
                    logger.info("### 异步批量更新索引记录成功! _index=[{}], bulkSize=[{}], hasFailure=[{}].",
                            getIndex(), bulkRequest.requests().size(), bulkResponse.hasFailures());
                } else {
                    logger.error("### 异步批量更新索引记录失败! _index=[{}], bulkSize=[{}], hasFailure=[{}], failureMessage=[{}].",
                            getIndex(), bulkRequest.requests().size(), bulkResponse.hasFailures(), bulkResponse.buildFailureMessage());
                }
            }

            @Override
            public void onFailure(Exception e) {
                logger.error("### 异步批量更新索引记录失败! _index=[{}], bulkSize=[{}].", getIndex(), bulkRequest.requests().size(), e);
            }

        });
    }

    @Override
    public long count(CountRequest request) throws IOException {
        request.indices(getIndex());
        CountResponse countResponse = getClient().count(request, DEFAULT);
        if (countResponse.getSuccessfulShards() != countResponse.getTotalShards()) {
            logger.warn("### 统计索引记录数失败! index=[{}], response=[{}].", getIndex(), countResponse.toString());
        }
        return countResponse.getCount();
    }

    @Override
    public Pagation<T> search(QueryBuilder query, SearchOptions searchOptions) throws Exception {
        SearchSourceBuilder searchSourceBuilder = SearchSourceBuilder.searchSource().query(query);
        if (searchOptions != null) {
            if (searchOptions.getFrom() >= 0 && searchOptions.getSize() > 0) {
                searchSourceBuilder.from(searchOptions.getFrom()).size(searchOptions.getSize());
            }
            if (searchOptions.getFields() != null && searchOptions.getFields().length > 0) {
                searchSourceBuilder.fetchSource(searchOptions.getFields(), null);
            }
            if (searchOptions.getSort() != null && searchOptions.getSort().size() > 0) {
                for (Map.Entry<String, SortOrder> entry : searchOptions.getSort().entrySet()) {
                    searchSourceBuilder.sort(entry.getKey(), entry.getValue());
                }
            }
            if (searchOptions.getTimeoutMillis() > 0) {
                searchSourceBuilder.timeout(TimeValue.timeValueMillis(searchOptions.getTimeoutMillis()));
            }
            if (searchOptions.isTrackTotalHits()) {
                searchSourceBuilder.trackTotalHits(searchOptions.isTrackTotalHits());
            }
            if (searchOptions.isExplain()) {
                searchSourceBuilder.explain(searchOptions.isExplain());
            }
            if (searchOptions.isProfile()) {
                searchSourceBuilder.profile(searchOptions.isProfile());
            }
        }
        SearchRequest searchRequest = Requests.searchRequest(getIndex()).source(searchSourceBuilder);
        if (searchOptions != null) {
            if (searchOptions.getRouting() != null) {
                searchRequest.routing(searchOptions.getRouting());
            }
            if (searchOptions.getSearchType() != null) {
                searchRequest.searchType(searchOptions.getSearchType());
            }
        }
        SearchResponse searchResponse = getClient().search(searchRequest, RequestOptions.DEFAULT);
        debugHelper.debugESQuery(searchSourceBuilder, searchResponse, logger);
        Pagation<T> pagation = new Pagation<>();
        pagation.setTotal(searchResponse.getHits() != null
                && searchResponse.getHits().getTotalHits() != null ?
                searchResponse.getHits().getTotalHits().value : 0);
        List<T> data = new ArrayList<>();
        if (pagation.getTotal() > 0) {
            for (SearchHit hit : searchResponse.getHits().getHits()) {
                data.add(convert(hit.getId(), hit.getSourceAsMap(), hit.getSourceAsString()));
            }
        }
        pagation.setData(data);
        return pagation;
    }

    @Override
    public Pagation<T> search(QueryBuilder query, String routing, int from, int size,
                              TreeMap<String, SortOrder> sort, SearchType searchType, int timeoutMillis, String... fields) throws Exception {
        SearchOptions searchOptions = SearchOptions.instance()
                .setRouting(routing)
                .setFrom(from).setSize(size)
                .setSort(sort)
                .setSearchType(searchType)
                .setTimeoutMillis(timeoutMillis)
                .setFields(fields);
        return search(query, searchOptions);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void afterPropertiesSet() throws Exception {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.entityClass = (Class) type.getActualTypeArguments()[0];
        checkEntityClass();
        if (schema != null && !isIndexExists()) {
            try {
                boolean success = createIndex();
                if (!success) {
                    logger.info("### 索引创建失败! index=[{}].", getIndex());
                } else {
                    logger.info("### 索引创建成功! index=[{}].", getIndex());
                }
            } catch (ElasticsearchStatusException e) {
                // Elasticsearch exception [type=resource_already_exists_exception, reason=index [sku/ZjgQSrf_Tc-67QezUosOug] already exists]
                // 从meta里判断索引已存在
                List<String> indexUUIDs = e.getMetadata("es.index_uuid");
                if (indexUUIDs != null && indexUUIDs.size() > 0) {
                    logger.warn("### 索引创建失败! 可能已存在! index=[{}], detailMessage=[{}].", getIndex(), e.getDetailedMessage());
                } else {
                    throw e;
                }
            }
        }
    }

    /**
     * 校验索引模型类
     */
    private void checkEntityClass() {
        ESDocument esDocument = entityClass.getAnnotation(ESDocument.class);
        Assert.notNull(esDocument,
                String.format("索引模型类[%s]缺少文档定义注解[@%s].",
                        entityClass.getSimpleName(), ESDocument.class.getSimpleName()));
        Assert.isTrue(esDocument.index().trim().length() > 0,
                String.format("索引模型类[%s]注解中定义的index无效!", entityClass.getSimpleName()));
        // 校验join-field定义
        com.demo.components.elasticsearch.annotation.JoinField joinField = esDocument.join();
        if (joinField.type() != JoinFieldTypeEnum.NONE) {
            Assert.isTrue(joinField.field().trim().length() > 0,
                    String.format("索引mappings中join-field属性名为空，请在索引模型类[%s]注解中定义!", entityClass.getSimpleName()));
            Assert.isTrue(joinField.name().trim().length() > 0,
                    String.format("索引join-field中name属性值为空, 请在索引模型类[%s]注解中定义!", entityClass.getSimpleName()));
        }
        Field idField = null;
        Field routingField = null;
        Field parentField = null;
        for (Field field : entityClass.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.getAnnotation(ESID.class) != null) {
                Assert.isTrue(idField == null,
                        String.format("索引模型类[%s]至多只能有一个被注解[@%s]标注的属性!", entityClass.getSimpleName(), ESID.class.getSimpleName()));
                idField = field;
            }
            if (field.getAnnotation(ESRouting.class) != null) {
                Assert.isTrue(routingField == null,
                        String.format("索引模型类[%s]至多只能有一个被注解[@%s]标注的属性!", entityClass.getSimpleName(), ESRouting.class.getSimpleName()));
                routingField = field;
            }
            if (field.getAnnotation(ESParent.class) != null) {
                Assert.isTrue(parentField == null,
                        String.format("索引模型类[%s]至多只能有一个被注解[@%s]标注的属性!", entityClass.getSimpleName(), ESParent.class.getSimpleName()));
                parentField = field;
            }
        }
        this.index = esDocument.index();
        this.schema = StringUtils.trimToNull(esDocument.schema());
        this.autoId = esDocument.autoId();
    }
}