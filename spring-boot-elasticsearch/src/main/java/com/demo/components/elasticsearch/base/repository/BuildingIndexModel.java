package com.demo.components.elasticsearch.base.repository;

import org.elasticsearch.common.xcontent.XContentBuilder;

/**
 * 构建索引时索引模型信息
 *
 * @author wude
 * @date 2020/1/19 17:32
 */
public class BuildingIndexModel {

    private String _id;
    private String _routing;
    private XContentBuilder xContentBuilder;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_routing() {
        return _routing;
    }

    public void set_routing(String _routing) {
        this._routing = _routing;
    }

    public XContentBuilder getxContentBuilder() {
        return xContentBuilder;
    }

    public void setxContentBuilder(XContentBuilder xContentBuilder) {
        this.xContentBuilder = xContentBuilder;
    }
}