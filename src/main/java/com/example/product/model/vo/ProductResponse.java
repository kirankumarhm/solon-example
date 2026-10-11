package com.example.product.model.vo;

import com.example.product.model.entity.Product;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Product View Object returned in API responses
 */
@ApiModel(description = "Product details response object")
public class ProductResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "Product Unique ID", example = "1")
    private Long id;

    @ApiModelProperty(value = "Product SKU", example = "PROD-LAPTOP-001")
    private String sku;

    @ApiModelProperty(value = "Product Name", example = "Developer Pro Laptop 16-inch")
    private String name;

    @ApiModelProperty(value = "Product Description", example = "High-performance workstation laptop")
    private String description;

    @ApiModelProperty(value = "Product Category", example = "Electronics")
    private String category;

    @ApiModelProperty(value = "Product Unit Price", example = "1899.99")
    private BigDecimal price;

    @ApiModelProperty(value = "Current Available Stock Quantity", example = "45")
    private Integer stockQuantity;

    @ApiModelProperty(value = "Product Status", example = "ACTIVE")
    private String status;

    @ApiModelProperty(value = "Creation timestamp")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "Last update timestamp")
    private LocalDateTime updatedAt;

    public ProductResponse() {
    }

    public static ProductResponse fromEntity(Product entity) {
        if (entity == null) {
            return null;
        }
        ProductResponse vo = new ProductResponse();
        vo.setId(entity.getId());
        vo.setSku(entity.getSku());
        vo.setName(entity.getName());
        vo.setDescription(entity.getDescription());
        vo.setCategory(entity.getCategory());
        vo.setPrice(entity.getPrice());
        vo.setStockQuantity(entity.getStockQuantity());
        vo.setStatus(entity.getStatus());
        vo.setCreatedAt(entity.getCreatedAt());
        vo.setUpdatedAt(entity.getUpdatedAt());
        return vo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
