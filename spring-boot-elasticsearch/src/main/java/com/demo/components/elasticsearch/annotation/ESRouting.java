package com.demo.components.elasticsearch.annotation;

import java.lang.annotation.*;

/**
 * 索引模型路由键值，对应索引记录_routing
 * 主要用于计算shard_num，默认值_id，因为parent-child结构模型时child须和parent在同一shard上
 * 一般说来：parent无须设置_routing，默认_id；child须把_routing值设为parent的_id
 *
 * @author wude
 * @date 2019-07-24 10:21
 * @see <a>https://www.elastic.co/guide/en/elasticsearch/reference/7.3/mapping-routing-field.html</a>
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ESRouting {

}