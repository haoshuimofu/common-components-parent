package com.demo.components.elasticsearch.model;

import com.demo.components.elasticsearch.annotation.*;
import com.demo.components.elasticsearch.base.model.BaseIndexModel;
import org.springframework.util.Assert;

/**
 * 服务站商品索引模型
 *
 * @author wude
 * @date 2020/2/27 17:19
 */
@ESDocument(index = "product_station", schema = "es-schema/product_station.json",
        join = @JoinField(type = JoinFieldTypeEnum.CHILD, field = "product_join_child", name = "station"))
public class ProductStation extends BaseIndexModel {

    @ESID
    @ESTransient
    private String id;
    @ESParent
    @ESRouting
    @ESField(name = "product_id")
    private String productId;
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

    public ProductStation(String stationId, String productId) {
        Assert.notNull(stationId, "服务站ID不能为空!");
        Assert.notNull(productId, "商品ID不能为空!");
        this.stationId = stationId;
        this.productId = productId;
        // 参考php，id=md5(stationId+productId)
        // this.id = DigestUtils.md5Hex(stationId + productId);
        this.id = productId + "#" + stationId;
    }

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