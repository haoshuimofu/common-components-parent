package com.demo.components.elasticsearch.model;

import com.demo.components.elasticsearch.annotation.*;
import com.demo.components.elasticsearch.base.model.BaseIndexModel;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 服务站商品索引模型
 *
 * @author wude
 * @date 2020/2/27 17:19
 */
@ESDocument(index = "station_product_replica", schema = "es-schema/station_product.json", join = @JoinField(type = JoinFieldTypeEnum.NONE))
public class StationProductReplica extends BaseIndexModel {
    @ESID
    @ESTransient
    private String id;
    private String name;
    @ESField(name = "name_analyze")
    private String nameAnalyze;
    @ESField(name = "base_name")
    private String baseName;
    @ESField(name = "search_tags")
    private String searchTags;
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
    @ESField(name = "manage_category_path")
    private List<Integer> manageCategoryPath;
    @ESField(name = "brand_id")
    private Integer brandId;

    @ESParent
    @ESField(name = "product_id")
    private String productId;
    @ESRouting
    @ESField(name = "station_id")
    private String stationId;
    @ESField(name = "station_has_stock")
    private Integer hasStock;
    @ESField(name = "station_status")
    private Integer status;
    @ESField(name = "station_price")
    private Double price;

    // CURRENT_TIMESTAMP ON UPDATE
    private long timestamp = System.currentTimeMillis() / 1000;

    public StationProductReplica() {
    }

    public StationProductReplica(String stationId, String productId) {
        Assert.notNull(stationId, "服务站ID不能为空!");
        Assert.notNull(productId, "商品ID不能为空!");
        this.stationId = stationId;
        this.productId = productId;
        this.id = productId + "#" + stationId;
    }

    public String generateId() {
        Assert.notNull(this.stationId, "服务站ID不能为空!");
        Assert.notNull(this.productId, "商品ID不能为空!");
        return this.productId + "#" + this.stationId;
    }

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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public Integer getHasStock() {
        return hasStock;
    }

    public void setHasStock(Integer hasStock) {
        this.hasStock = hasStock;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}