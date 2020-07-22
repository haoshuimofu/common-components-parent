package com.demo.components.elasticsearch.base.model;

/**
 * 索引模型基类
 *
 * @author wude
 * @date 2019-08-12 19:05
 */
public class BaseIndexModel {

    private String _id;

    private String _routing;

    public BaseIndexModel id(String id) {
        this._id = id;
        return this;
    }

    public BaseIndexModel routing(String routing) {
        this._routing = routing;
        return this;
    }

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
}