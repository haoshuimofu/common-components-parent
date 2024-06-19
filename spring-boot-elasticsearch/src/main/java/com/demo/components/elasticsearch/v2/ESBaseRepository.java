package com.demo.components.elasticsearch.v2;

import com.alibaba.fastjson.JSON;
import com.demo.components.elasticsearch.config.ESRestClientContainer;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
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

    /**
     * <p>
     * abortOnVersionConflict 是 Elasticsearch 中的一个请求参数，主要用于控制在执行写操作（如索引文档、更新文档或删除文档）时，如果遇到版本冲突（version conflict）应该如何处理。版本冲突通常发生在以下场景：当你尝试更新或删除一个文档，但该文档在你上次读取之后已经被其他进程修改过。
     * 当 abortOnVersionConflict 设置为 true（默认值通常是 true），如果遇到版本冲突，Elasticsearch 将不会执行该操作，并且会返回一个错误响应，表明发生了版本冲突。这样可以防止意外覆盖其他进程所做的更改，保持数据的一致性。
     * 相反，如果将其设置为 false，即使发生版本冲突，Elasticsearch 也会尝试继续执行操作。在这种情况下，具体的行为可能依赖于操作类型和其它参数设置，但通常意味着你的操作可能会默默地失败，或者在某些情况下按照特定的重试逻辑进行处理。
     * 在使用 DELETE BY QUERY 或 Update By Query 这样的批量操作时，这个参数同样适用，它可以帮助决定在批量操作中遇到单个文档的版本冲突时，是应该停止整个操作还是忽略冲突并继续执行剩余的操作。
     * 例如，在发送一个 DELETE BY QUERY 请求时，你可以通过添加 conflicts=proceed 到URL参数或在请求体中设置 "conflicts": "proceed" 来达到类似的效果，这等价于设置 abortOnVersionConflict 为 false。
     * </p>
     *
     * <p>
     * 在 Elasticsearch 中，提及 "slices" 参数，最有可能关联到的是在 search 请求中的 slice 参数或者是在 scroll、reindex、update_by_query 等操作中的 slices 参数。这个参数的作用是将一个大的查询请求切分为多个较小的“切片”（slices）并行执行，以此来加速处理过程，特别是在处理大量数据的查询、更新或聚合任务时。
     * 具体来说，slices 参数的工作原理和用途如下：
     * 并行处理：通过将查询或操作分割成多个切片，Elasticsearch 可以在多个分片（shards）上并行执行这些切片，从而利用多核处理器和分布式系统的并行处理能力，减少整体处理时间。
     * 控制并发度：用户可以通过设置 slices 参数来指定切片的数量。如果未指定或设置为 auto，Elasticsearch 会尝试根据索引的分片数自动选择一个合适的切片数。手动设置切片数可以让你根据实际情况微调并行度，以达到最优性能。
     * 在大查询或批处理中的应用：这个特性特别适用于大数据量的查询、重索引（reindexing）、更新查询（update_by_query）或删除查询（delete_by_query）等操作，这些操作通常涉及大量数据的处理，通过切片可以显著提高效率。
     * </p>
     *
     * @param queryBuilder
     * @param indices
     */
    public void deleteByQuery(QueryBuilder queryBuilder, String indices) {
        DeleteByQueryRequest request = new DeleteByQueryRequest(indices);
        request.setQuery(queryBuilder)
                .setSlices(Runtime.getRuntime().availableProcessors())
                .setAbortOnVersionConflict(false)
                .setMaxDocs(10_000);
        getClient().deleteByQueryAsync(request, RequestOptions.DEFAULT,
                new ActionListener<BulkByScrollResponse>() {
                    @Override
                    public void onResponse(BulkByScrollResponse bulkByScrollResponse) {
                        log.info("[ES] delete_by_query ok, query={}, response={}",
                                queryBuilder.toString(), bulkByScrollResponse.toString());
                    }

                    @Override
                    public void onFailure(Exception e) {
                        log.error("[ES] delete_by_query fail, query={}", queryBuilder.toString(), e);
                    }
                });
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

}
