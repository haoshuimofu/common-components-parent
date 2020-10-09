package com.demo.components.elasticsearch.base.repository;

import com.demo.components.elasticsearch.Pagation;
import com.demo.components.elasticsearch.request.SearchOptions;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 通用CRUD接口
 *
 * @author wude
 * @date 2019-07-25 19:56
 */
public interface CrudRepository<T> {

    /**
     * 获取索引名称
     *
     * @return T对应index
     */
    String getIndex();

    /**
     * 索引是否已存在
     *
     * @return true/false 索引已/不存在
     * @throws IOException
     */
    boolean isIndexExists() throws IOException;

    /**
     * 删除索引
     *
     * @return true/false删除索引成功/失败
     * @throws IOException
     */
    boolean deleteIndex() throws IOException;

    /**
     * 创建索引模型类对应索引
     *
     * @return
     * @throws IOException
     */
    boolean createIndex() throws IOException;

    /**
     * 保存索引记录
     *
     * @param model 索引模型对象
     * @return
     * @throws Exception
     */
    boolean save(T model) throws Exception;

    /**
     * 保存索引记录
     *
     * @param model  索引模型对象
     * @param create opType=Create，为true时_id对应记录若存在则保存失败
     * @return
     * @throws Exception
     */
    boolean save(T model, boolean create) throws Exception;

    /**
     * 异步保存索引记录
     *
     * @param model
     * @throws Exception
     */
    void saveAsync(T model) throws Exception;

    /**
     * 异步保存索引记录
     *
     * @param model
     * @param create
     * @return
     * @throws Exception
     */
    void saveAsync(T model, boolean create) throws Exception;

    /**
     * 批量保存索引记录
     *
     * @param models
     * @throws Exception
     */
    boolean bulkSave(List<T> models) throws Exception;

    /**
     * 批量保存索引记录
     *
     * @param models
     * @param create
     * @return
     * @throws Exception
     */
    boolean bulkSave(List<T> models, boolean create) throws Exception;

    /**
     * 批量异步保存索引记录
     *
     * @param models
     * @throws Exception
     */
    void bulkSaveAsync(List<T> models) throws Exception;

    /**
     * 批量异步保存索引记录
     *
     * @param models
     * @param create
     * @throws Exception
     */
    void bulkSaveAsync(List<T> models, boolean create) throws Exception;

    /**
     * 删除索引记录
     *
     * @param id 索引记录_id
     * @return
     * @throws IOException
     */
    boolean deleteById(String id) throws IOException;

    /**
     * 删除索引记录
     *
     * @param id      索引记录_id
     * @param routing _routing值
     * @return
     * @throws IOException
     */
    boolean deleteById(String id, String routing) throws IOException;

    /**
     * 批量删除索引记录
     *
     * @param ids 索引记录_id集合
     * @return
     * @throws IOException
     */
    boolean deleteByIds(List<String> ids) throws IOException;

    /**
     * 批量删除索引记录
     *
     * @param ids           索引记录_id集合
     * @param globalRouting 统一_routing
     * @return
     * @throws IOException
     */
    boolean deleteByIds(List<String> ids, String globalRouting) throws IOException;

    /**
     * 批量删除索引记录
     *
     * @param ids      索引记录_id集合
     * @param routings 索引记录对应_routing集合
     * @return
     * @throws IOException
     */
    boolean deleteByIds(List<String> ids, List<String> routings) throws IOException;

    /**
     * 查询索引记录
     *
     * @param id 索引记录_id
     * @return
     * @throws IOException
     */
    T getById(String id) throws IOException;

    /**
     * 查询索引记录
     *
     * @param id      索引记录_id
     * @param routing _routing值
     * @return
     * @throws IOException
     */
    T getById(String id, String routing) throws IOException;

    /**
     * 批量查询索引记录
     *
     * @param ids 索引记录_id集合
     * @return
     * @throws Exception
     */
    List<T> getByIds(List<String> ids) throws Exception;

    /**
     * 批量查询索引记录
     *
     * @param ids           索引记录_id集合
     * @param globalRouting 统一_routing值
     * @return
     * @throws Exception
     */
    List<T> getByIds(List<String> ids, String globalRouting) throws Exception;

    /**
     * 更新索引记录
     *
     * @param model
     * @return
     * @throws Exception
     */
    boolean update(T model) throws Exception;

    /**
     * 异步更新索引记录
     *
     * @param model
     */
    void updateAsync(T model) throws Exception;

    /**
     * 批量更新索引记录
     *
     * @param models
     * @return
     * @throws Exception
     */
    boolean bulkUpdate(List<T> models) throws Exception;

    /**
     * 批量异步更新索引记录
     *
     * @param models
     * @throws Exception
     */
    void bulkUpdateAsync(List<T> models) throws Exception;

    /**
     * 统计索引记录数
     *
     * @param request 统计请求
     * @return
     * @throws IOException
     */
    long count(CountRequest request) throws IOException;

    /**
     * 简单分页查询
     *
     * @param query         查询条件
     * @param searchOptions 查询选项
     * @return
     * @throws
     */
    Pagation<T> search(QueryBuilder query, SearchOptions searchOptions) throws Exception;

    /**
     * 简单分页查询
     *
     * @param query         查询条件
     * @param routing       路由键值
     * @param from          起始位置
     * @param size          分页大小
     * @param sort          排序条件
     * @param searchType    search_type
     * @param timeoutMillis 查询熔断超时时间
     * @param fields        查询字段
     * @return
     * @throws
     */
    Pagation<T> search(QueryBuilder query, String routing, int from, int size,
                       TreeMap<String, SortOrder> sort, SearchType searchType, int timeoutMillis, String... fields) throws Exception;

    /**
     * 搜索结果source转索引模型实例
     *
     * @param id             索引记录ID
     * @param sourceAsMap
     * @param sourceAsString
     * @return
     */
    T convert(String id, Map<String, Object> sourceAsMap, String sourceAsString);

}