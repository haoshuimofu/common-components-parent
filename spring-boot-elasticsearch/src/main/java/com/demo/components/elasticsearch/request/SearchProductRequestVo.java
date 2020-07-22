package com.demo.components.elasticsearch.request;

import java.util.Date;

/**
 * 搜索商品请求
 *
 * @Author wude
 * @Create 2019-07-25 14:11
 */
public class SearchProductRequestVo {

    private String keywords;

    private Double priceStart;
    private Double priceEnd;

    private Date publishTimeStart;
    private Date publishTimeEnd;

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Double getPriceStart() {
        return priceStart;
    }

    public void setPriceStart(Double priceStart) {
        this.priceStart = priceStart;
    }

    public Double getPriceEnd() {
        return priceEnd;
    }

    public void setPriceEnd(Double priceEnd) {
        this.priceEnd = priceEnd;
    }

    public Date getPublishTimeStart() {
        return publishTimeStart;
    }

    public void setPublishTimeStart(Date publishTimeStart) {
        this.publishTimeStart = publishTimeStart;
    }

    public Date getPublishTimeEnd() {
        return publishTimeEnd;
    }

    public void setPublishTimeEnd(Date publishTimeEnd) {
        this.publishTimeEnd = publishTimeEnd;
    }
}