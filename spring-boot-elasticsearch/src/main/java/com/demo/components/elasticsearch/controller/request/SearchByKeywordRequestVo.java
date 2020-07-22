package com.demo.components.elasticsearch.controller.request;

/**
 * @author wude
 * @date 2020/2/15 21:23
 */
public class SearchByKeywordRequestVo {

    private String stationId;

    private String keyword;

    private int skip;

    private int limit;

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getSkip() {
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}