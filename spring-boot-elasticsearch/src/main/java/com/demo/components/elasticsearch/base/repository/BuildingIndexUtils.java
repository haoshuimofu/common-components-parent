package com.demo.components.elasticsearch.base.repository;

import com.alibaba.fastjson.JSON;
import com.demo.components.elasticsearch.annotation.*;
import com.demo.components.elasticsearch.base.model.BaseIndexModel;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.search.SearchHit;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 索引构建工具类
 *
 * @author wude
 * @date 2020/1/19 17:34
 */
public class BuildingIndexUtils {

    /**
     * 把索引模型实例对象预处理成{@link BuildingIndexModel}对象
     *
     * @param model  索引模型实例对象
     * @param clazz  索引模型类
     * @param <T>    模型模型类泛型
     * @param autoId 当传入_id值为空时是否允许elasticsearch自动为其生成_id
     * @return 索引构建时用对象
     * @throws Exception
     */
    public static <T extends BaseIndexModel> BuildingIndexModel buildingModel(T model, Class<T> clazz, boolean autoId) throws Exception {
        // _id先取BaseIndexModel中_id属性值，如果为空则再取T类中被@ESID注解标注的属性值，最后为空则报错提示
        // _routing先取BaseIndexModel中_routing属性值，如果为空则再取T类中被@ESRouting注解标注的属性值
        // _id必填，为空报错；_routing非必填，T类有@ESRouting但最后_routing值为空则报错
        Field idField = null;
        Field routingField = null;
        Field parentField = null;
        XContentBuilder xb = XContentFactory.jsonBuilder().startObject();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.getAnnotation(ESID.class) != null) {
                idField = field;
            }
            if (field.getAnnotation(ESRouting.class) != null) {
                routingField = field;
            }

            if (field.getAnnotation(ESParent.class) != null) {
                parentField = field;
            }

            // 构建source
            if (field.getAnnotation(ESTransient.class) != null) {
                continue; // 忽略，被此注解标注的属性不参与索引构建
            }
            Object fieldValue = field.get(model);
            if (fieldValue == null) {
                continue; // 属性值为null跳过
            }
            ESField esField = field.getAnnotation(ESField.class);
            String fieldName = getMappingFieldName(field, esField);
            if (esField != null && esField.datatype() != DataTypeEnum.CORE) {
                switch (esField.datatype()) {
                    case OBJECT: {
                        xb.field(fieldName, parseObjectField(fieldValue));
                        break;
                    }
                    case NESETED: {
                        xb.field(fieldName, parseNestedField((List<Object>) fieldValue));
                    }
                    default:
                        break;
                }

            } else {
                xb.field(fieldName, fieldValue);
            }
        }
        // join-field
        ESDocument esDocument = clazz.getAnnotation(ESDocument.class);
        JoinField joinField = esDocument.join();
        switch (joinField.type()) {
            case NONE:
                break;
            case PARENT: {
                xb.field(joinField.field(), joinField.name()); // 简写模式
                break;
            }
            case CHILD: {
                Assert.isTrue(parentField != null && parentField.get(model) != null, "索引记录join-field中parent为空!");
                Map<String, Object> joinFieldMap = new HashMap<>();
                joinFieldMap.put("parent", parentField.get(model).toString());
                joinFieldMap.put("name", joinField.name());
                xb.field(joinField.field(), joinFieldMap);
                break;
            }
            default:
                break;
        }
        xb.endObject();
        String _id = model.get_id();
        if (StringUtils.isBlank(_id) && idField != null) {
            Object idValue = idField.get(model);
            if (idValue != null) {
                _id = idValue.toString();
            }
        }
        _id = StringUtils.trimToNull(_id);
        Assert.isTrue(autoId || _id != null, "索引记录_id为空!");
        String _routing = StringUtils.trimToNull(model.get_routing());
        if (_routing == null && routingField != null) {
            Object routingValue = routingField.get(model);
            if (routingValue != null) {
                _routing = StringUtils.trimToNull(routingValue.toString());
            }
            Assert.notNull(_routing, "索引记录_routing为空!");
        }
        BuildingIndexModel buildingIndexModel = new BuildingIndexModel();
        buildingIndexModel.set_id(_id);
        buildingIndexModel.set_routing(_routing);
        buildingIndexModel.setxContentBuilder(xb);
        return buildingIndexModel;
    }

    /**
     * 获取索引映射名
     *
     * @param field
     * @param esField
     * @return
     */
    private static String getMappingFieldName(Field field, ESField esField) {
        String fieldName = null;
        if (esField != null) {
            fieldName = esField.name().trim();
        }
        if (fieldName == null || fieldName.length() == 0) {
            fieldName = field.getName();
        }
        return fieldName;
    }

    /**
     * 将查询记录记录的sourceMap转成Model类实例
     *
     * @param sourceMap
     * @param id
     * @return
     */
    public static <T extends BaseIndexModel> T convertSourceMapToModel(String id, Map<String, Object> sourceMap, Class<T> entityClass) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("_id", id);
        if (sourceMap != null && sourceMap.size() > 0) {
            Map<String, String> fieldsNameMapping = getFieldNameMapping(entityClass);
            for (Map.Entry<String, Object> entry : sourceMap.entrySet()) {
                jsonMap.put(fieldsNameMapping.get(entry.getKey()), entry.getValue());
            }
        }
        // sourceMap并不一定包含_id，判断并处理
        for (Field field : entityClass.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.getAnnotation(ESID.class) != null) {
                ESField esField = field.getAnnotation(ESField.class);
                jsonMap.put(esField == null ? field.getName() : esField.name(), id);
                break;
            }
        }
        return JSON.parseObject(JSON.toJSONString(jsonMap), entityClass);
    }

    /**
     * 获取索引文档字段和模型属性名的映射关系
     *
     * @return
     */
    private static <T extends BaseIndexModel> Map<String, String> getFieldNameMapping(Class<T> entityClass) {
        Map<String, String> fieldsNameMap = new HashMap<>();
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            ESField esField = field.getAnnotation(ESField.class);
            fieldsNameMap.put(esField == null ? field.getName() : esField.name(), field.getName());
        }
        return fieldsNameMap;
    }

    /**
     * 将SearchHit转换成索引模型对象
     *
     * @param searchHit
     * @return
     */
    public static <T extends BaseIndexModel> T convertSearchHit2Model(SearchHit searchHit, Class<T> entityClass) {
        return convertSourceMapToModel(searchHit.getId(), searchHit.getSourceAsMap(), entityClass);
    }


    /**
     * 构造IndexRequest实例
     *
     * @param model
     * @param clazz
     * @param create
     * @param autoId
     * @param index
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T extends BaseIndexModel> IndexRequest buildIndexRequest(T model, Class<T> clazz, boolean create,
                                                                            boolean autoId, String index) throws Exception {
        BuildingIndexModel buildingIndexModel = BuildingIndexUtils.buildingModel(model, clazz, autoId);
        IndexRequest request = new IndexRequest(index)
                .id(buildingIndexModel.get_id())
                .create(create)
                .source(buildingIndexModel.getxContentBuilder());
        if (buildingIndexModel.get_routing() != null) {
            request.routing(buildingIndexModel.get_routing());
        }
        return request;
    }

    /**
     * 构建IndexRequest BulkRequest实例
     *
     * @param models
     * @param clazz
     * @param create
     * @param autoId
     * @param index
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T extends BaseIndexModel> BulkRequest bulkBuildIndexRequest(List<T> models, Class<T> clazz,
                                                                               boolean create, boolean autoId, String index) throws Exception {
        // 如果所有Model都指定了同一个_routing值, 那么在BulkRequest对象上显式赋值_routing
        boolean all = true;
        Set<String> routings = new HashSet<>();
        IndexRequest indexRequest;
        BulkRequest bulkRequest = new BulkRequest(index);
        for (T model : models) {
            indexRequest = BuildingIndexUtils.buildIndexRequest(model, clazz, create, autoId, index);
            bulkRequest.add(indexRequest);
            if (indexRequest.routing() == null) {
                all = false;
                continue;
            }
            routings.add(indexRequest.routing());
        }
        if (all && routings.size() == 1) {
            bulkRequest.routing(routings.iterator().next());
        }
        return bulkRequest;
    }


    /**
     * 构建UpdateRequest实例
     *
     * @param model
     * @param clazz
     * @param index
     * @param retryOnConflict
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T extends BaseIndexModel> UpdateRequest buildUpdateRequest(T model, Class<T> clazz, String index, int retryOnConflict) throws Exception {
        BuildingIndexModel buildingIndexModel = BuildingIndexUtils.buildingModel(model, clazz, false);
        UpdateRequest request = new UpdateRequest(index, buildingIndexModel.get_id())
                .doc(buildingIndexModel.getxContentBuilder());
        if (buildingIndexModel.get_routing() != null) {
            request.routing(buildingIndexModel.get_routing());
        }
        if (retryOnConflict > 0) {
            request.retryOnConflict(retryOnConflict);
        }
        return request;
    }

    /**
     * 构建UpdateRequest BulkRequest实例
     *
     * @param models
     * @param clazz
     * @param index
     * @param retryOnConflict
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T extends BaseIndexModel> BulkRequest buildBulkUpdateRequest(List<T> models, Class<T> clazz, String index, int retryOnConflict) throws Exception {
        // 如果所有Model都指定了同一个_routing值, 那么在BulkRequest对象上显式赋值_routing
        boolean all = true;
        Set<String> routings = new HashSet<>();
        UpdateRequest updateRequest;
        BulkRequest bulkRequest = new BulkRequest(index);
        for (T model : models) {
            updateRequest = BuildingIndexUtils.buildUpdateRequest(model, clazz, index, retryOnConflict);
            bulkRequest.add(updateRequest);
            if (updateRequest.routing() == null) {
                all = false;
                continue;
            }
            routings.add(updateRequest.routing());
        }
        if (all && routings.size() == 1) {
            bulkRequest.routing(routings.iterator().next());
        }
        return bulkRequest;
    }

    private static Map<String, Object> parseObjectField(Object fieldValue) throws Exception {
        if (fieldValue == null) return null;
        Map<String, Object> objMap = new HashMap<>();
        for (Field field : fieldValue.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value = field.get(fieldValue);
            if (value == null) continue;
            ESField esField = field.getAnnotation(ESField.class);
            String nestedFieldName = (esField == null || esField.name().trim().length() == 0) ?
                    field.getName() : esField.name();
            objMap.put(nestedFieldName, value);
        }
        return objMap.isEmpty() ? null : objMap;
    }

    private static List<Map<String, Object>> parseNestedField(List<Object> fieldValue) throws Exception {
        if (fieldValue == null || fieldValue.isEmpty()) return null;
        List<Map<String, Object>> nestedMap = new ArrayList<>();
        for (Object element : fieldValue) {
            Map<String, Object> elementMap = parseObjectField(element);
            if (elementMap != null && !elementMap.isEmpty()) {
                nestedMap.add(elementMap);
            }
        }
        return nestedMap.isEmpty() ? null : nestedMap;
    }

}