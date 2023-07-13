package com.demo.components.elasticsearch.model;

import com.demo.components.elasticsearch.annotation.*;
import com.demo.components.elasticsearch.base.model.BaseIndexModel;

import java.util.Date;
import java.util.List;

/**
 * @author wude
 * @date 2020/2/17 11:26
 */
@ESDocument(index = "demo_index", schema = "es-schema/demo_index.json", join = @JoinField(type = JoinFieldTypeEnum.NONE))
public class Demo extends BaseIndexModel {

    @ESID
    @ESTransient
    @ESRouting
    private String id;
    private String name;
    private String title;
    private Integer status;
    private Date timestamp;

    @ESField(name = "detail", datatype = DataTypeEnum.OBJECT)
    private DemoDetail detail;

    @ESField(datatype = DataTypeEnum.NESTED)
    private List<DemoItem> items;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public DemoDetail getDetail() {
        return detail;
    }

    public void setDetail(DemoDetail detail) {
        this.detail = detail;
    }

    public List<DemoItem> getItems() {
        return items;
    }

    public void setItems(List<DemoItem> items) {
        this.items = items;
    }
}