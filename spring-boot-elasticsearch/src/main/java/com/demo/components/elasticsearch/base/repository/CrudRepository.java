package com.demo.components.elasticsearch.base.repository;

import java.io.IOException;
import java.util.List;

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
     * @return
     */
    String getIndex();

    /**
     * 索引模型类对应索引在ES集群中是否已存在
     *
     * @return
     * @throws IOException
     */
    boolean isIndexExists() throws IOException;

    /**
     * 删除索引模型类对应索引
     *
     * @return
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
     * 根据_id删除索引记录
     *
     * @param id
     * @return
     * @throws IOException
     */
    boolean deleteById(String id) throws IOException;

    /**
     * 根据_id 和_routing删除索引记录
     *
     * @param id
     * @param routing
     * @return
     * @throws IOException
     */
    boolean deleteById(String id, String routing) throws IOException;

    /**
     * 批量删除索引记录
     *
     * @param ids
     * @return
     * @throws IOException
     */
    boolean deleteByIds(List<String> ids) throws IOException;

    /**
     * 根据_id集合和统一_routing批量删除索引记录
     *
     * @param ids
     * @param globalRouting
     * @return
     * @throws IOException
     */
    boolean deleteByIds(List<String> ids, String globalRouting) throws IOException;

    /**
     * 根据_id集合和等长度_routing集合批量删除索引记录
     *
     * @param ids
     * @param routings
     * @return
     */
    boolean deleteByIds(List<String> ids, List<String> routings) throws IOException;

    /**
     * 根据_id查找索引记录
     *
     * @param id
     * @return
     * @throws IOException
     */
    T getById(String id) throws IOException;

    /**
     * 根据_id和_routing查找索引记录
     *
     * @param id
     * @return
     * @throws IOException
     */
    T getById(String id, String routing) throws IOException;

    /**
     * 根据_id集合查找索引记录
     *
     * @param ids
     * @return
     * @throws IOException
     */
    List<T> getByIds(List<String> ids) throws IOException;

    /**
     * 根据_id集合和统一的_routing查找索引记录
     *
     * @param ids
     * @param globalRouting
     * @return
     * @throws IOException
     */
    List<T> getByIds(List<String> ids, String globalRouting) throws IOException;

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
}