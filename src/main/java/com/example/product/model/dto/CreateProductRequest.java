package com.example.product.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.noear.solon.validation.annotation.Length;
import org.noear.solon.validation.annotation.NotBlank;
import org.noear.solon.validation.annotation.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Request payload for creating a new product
 */
@ApiModel(description = "Payload for creating a product")
public class CreateProductRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "Stock Keeping Unit unique code", required = true, example = "PROD-MONITOR-001")
    @NotBlank(message = "Product SKU must not be blank")
    @Length(min = 3, max = 64, message = "SKU must be between 3 and 64 characters")
    private String sku;

    @ApiModelProperty(value = "Product title/name", required = true, example = "Ultra HD 4K Monitor 27-inch")
    @NotBlank(message = "Product name must not be blank")
    @Length(min = 2, max = 255, message = "Product name must be between 2 and 255 characters")
    private String name;

    @ApiModelProperty(value = "Product detailed description", example = "IPS panel with HDR 400 and USB-C 90W power delivery")
    private String description;

    @ApiModelProperty(value = "Product Category", required = true, example = "Electronics")
    @NotBlank(message = "Category must not be blank")
    private String category;

    @ApiModelProperty(value = "Unit Price in USD", required = true, example = "449.99")
    @NotNull(message = "Price must not be null")
    private BigDecimal price;

    @ApiModelProperty(value = "Initial Stock Quantity", required = true, example = "50")
    @NotNull(message = "Stock quantity must not be null")
    private Integer stockQuantity;

    @ApiModelProperty(value = "Product Status (ACTIVE, INACTIVE)", example = "ACTIVE")
    private String status = "ACTIVE";

    public CreateProductRequest() {
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
}
