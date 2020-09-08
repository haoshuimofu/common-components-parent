package com.demo.components.elasticsearch.request;

import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.search.sort.SortOrder;

import java.util.TreeMap;

/**
 * 查询选项
 *
 * @author wude
 * @date 2020/8/8 19:20
 */
public class SearchOptions {

    private int from;
    private int size;
    private String[] fields;
    private TreeMap<String, SortOrder> sort;
    private String routing;
    private int timeoutMillis;
    private SearchType searchType;
    private boolean trackTotalHits; // 跟踪总记录数
    private boolean explain;        // 评分计算分析
    private boolean profile;        // 查询性能分析

    private SearchOptions() {

    }

    public static SearchOptions instance() {
        return new SearchOptions();
    }

    public int getFrom() {
        return from;
    }

    public SearchOptions setFrom(int from) {
        this.from = from;
        return this;
    }

    public int getSize() {
        return size;
    }

    public SearchOptions setSize(int size) {
        this.size = size;
        return this;
    }

    public String[] getFields() {
        return fields;
    }

    public SearchOptions setFields(String[] fields) {
        this.fields = fields;
        return this;
    }

    public TreeMap<String, SortOrder> getSort() {
        return sort;
    }

    public SearchOptions setSort(TreeMap<String, SortOrder> sort) {
        this.sort = sort;
        return this;
    }

    public String getRouting() {
        return routing;
    }

    public SearchOptions setRouting(String routing) {
        this.routing = routing;
        return this;
    }

    public int getTimeoutMillis() {
        return timeoutMillis;
    }

    public SearchOptions setTimeoutMillis(int timeoutMillis) {
        this.timeoutMillis = timeoutMillis;
        return this;
    }

    public SearchType getSearchType() {
        return searchType;
    }

    public SearchOptions setSearchType(SearchType searchType) {
        this.searchType = searchType;
        return this;
    }

    public boolean isTrackTotalHits() {
        return trackTotalHits;
    }

    public SearchOptions setTrackTotalHits(boolean trackTotalHits) {
        this.trackTotalHits = trackTotalHits;
        return this;
    }

    public boolean isExplain() {
        return explain;
    }

    public void setExplain(boolean explain) {
        this.explain = explain;
    }

    public boolean isProfile() {
        return profile;
    }

    public void setProfile(boolean profile) {
        this.profile = profile;
    }
}