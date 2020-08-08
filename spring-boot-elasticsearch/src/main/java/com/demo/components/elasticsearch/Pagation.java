package com.demo.components.elasticsearch;

import java.util.List;

/**
 * @author wude
 * @date 2020/8/8 18:35
 */
public class Pagation<T> {

    private long total;
    private List<T> data;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}