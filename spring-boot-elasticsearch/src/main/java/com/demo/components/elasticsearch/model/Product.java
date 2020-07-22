package com.demo.components.elasticsearch.model;

import com.demo.components.elasticsearch.annotation.*;
import com.demo.components.elasticsearch.base.model.BaseIndexModel;

import java.util.List;

/**
 * @author wude
 * @date 2020/2/15 18:52
 */
@ESDocument(index = "product_station", schema = "es-schema/product_station.json",
        join = @JoinField(type = JoinFieldTypeEnum.PARENT, field = "product_join_child", name = "product"))
public class Product extends BaseIndexModel {

    @ESID
    @ESTransient
    private String id;
    @ESField(name = "product_id")
    private String productId;
    private String name;
    @ESField(name = "name_analyze")
    private String nameAnalyze;
    @ESField(name = "base_name")
    private String baseName;
    //@ESAnalyzer
    @ESField(name = "search_tags")
    private String searchTags;
    //@ESAnalyzer(from = "searchTags")
    @ESField(name = "search_tags_analyzer")
    private String searchTagsAnalyzer;
    @ESField(name = "total_sales")
    private Integer totalSales;
    @ESField(name = "update_time")
    private Long updateTime;
    private String category;
    @ESField(name = "category_path")
    private List<String> categoryPath;
    @ESField(name = "is_delete")
    private Integer isDelete;
    @ESField(name = "property_search")
    private List<String> propertySearch;
    @ESField(name = "oid")
    private Integer oid;
    @ESField(name = "manage_category")
    private Integer manageCategory;
    @ESField(name = "manage_category_path")
    private List<Integer> manageCategoryPath;
    @ESField(name = "brand_id")
    private Integer brandId;

    @ESField(name = "station_id")
    private String stationId = "0"; // 增加服务站ID=0以表示不绑定服务站的商品数据

    // CURRENT_TIMESTAMP ON UPDATE
    @ESField(name = "row_update_time")
    private long rowUpdateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameAnalyze() {
        return nameAnalyze;
    }

    public void setNameAnalyze(String nameAnalyze) {
        this.nameAnalyze = nameAnalyze;
    }

    public String getBaseName() {
        return baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    public String getSearchTags() {
        return searchTags;
    }

    public void setSearchTags(String searchTags) {
        this.searchTags = searchTags;
    }

    public String getSearchTagsAnalyzer() {
        return searchTagsAnalyzer;
    }

    public void setSearchTagsAnalyzer(String searchTagsAnalyzer) {
        this.searchTagsAnalyzer = searchTagsAnalyzer;
    }

    public Integer getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(Integer totalSales) {
        this.totalSales = totalSales;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getCategoryPath() {
        return categoryPath;
    }

    public void setCategoryPath(List<String> categoryPath) {
        this.categoryPath = categoryPath;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public List<String> getPropertySearch() {
        return propertySearch;
    }

    public void setPropertySearch(List<String> propertySearch) {
        this.propertySearch = propertySearch;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public Integer getManageCategory() {
        return manageCategory;
    }

    public void setManageCategory(Integer manageCategory) {
        this.manageCategory = manageCategory;
    }

    public List<Integer> getManageCategoryPath() {
        return manageCategoryPath;
    }

    public void setManageCategoryPath(List<Integer> manageCategoryPath) {
        this.manageCategoryPath = manageCategoryPath;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public long getRowUpdateTime() {
        return rowUpdateTime;
    }

    public void setRowUpdateTime(long rowUpdateTime) {
        this.rowUpdateTime = rowUpdateTime;
    }
}