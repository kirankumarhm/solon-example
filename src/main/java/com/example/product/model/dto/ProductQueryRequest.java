package com.example.product.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Filter criteria and pagination query for product list search
 */
@ApiModel(description = "Product search and pagination filter query")
public class ProductQueryRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "Search keyword matched against SKU, Name, and Description", example = "laptop")
    private String keyword;

    @ApiModelProperty(value = "Filter by exact category", example = "Electronics")
    private String category;

    @ApiModelProperty(value = "Filter by status (ACTIVE, INACTIVE, DISCONTINUED)", example = "ACTIVE")
    private String status;

    @ApiModelProperty(value = "Minimum price bound", example = "100.00")
    private BigDecimal minPrice;

    @ApiModelProperty(value = "Maximum price bound", example = "2000.00")
    private BigDecimal maxPrice;

    @ApiModelProperty(value = "Page number (1-based index)", example = "1")
    private int page = 1;

    @ApiModelProperty(value = "Page size (records per page)", example = "10")
    private int size = 10;

    public ProductQueryRequest() {
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public int getPage() {
        return page <= 0 ? 1 : page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size <= 0 ? 10 : Math.min(size, 100);
    }

    public void setSize(int size) {
        this.size = size;
    }
}
