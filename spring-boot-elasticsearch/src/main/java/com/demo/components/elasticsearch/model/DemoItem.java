package com.demo.components.elasticsearch.model;

import com.demo.components.elasticsearch.annotation.ESField;

/**
 * @author wude
 * @date 2020/7/11 10:10
 */
public class DemoItem {

    @ESField(name = "item_name")
    private String itemName;
    @ESField(name = "item_title")
    private String itemTitle;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }
}