package com.demo.components.elasticsearch.annotation;

/**
 * Elasticsearch 数据类型枚举定义
 * 具体参考: https://www.elastic.co/guide/en/elasticsearch/reference/7.8/mapping-types.html
 *
 * @author wude
 * @date 2020/7/11 11:01
 */
public enum DataTypeEnum {

    CORE,   // Core data types
    OBJECT, // Complex data type -> Object
    NESTED  // Complex data type -> Nested

}