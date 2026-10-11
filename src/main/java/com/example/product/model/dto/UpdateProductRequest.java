package com.example.product.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.noear.solon.validation.annotation.Length;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Request payload for updating an existing product
 */
@ApiModel(description = "Payload for updating a product")
public class UpdateProductRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "Product title/name", example = "Ultra HD 4K Monitor 27-inch (V2)")
    @Length(min = 2, max = 255, message = "Product name must be between 2 and 255 characters")
    private String name;

    @ApiModelProperty(value = "Product detailed description", example = "Updated specifications")
    private String description;

    @ApiModelProperty(value = "Product Category", example = "Electronics")
    private String category;

    @ApiModelProperty(value = "Unit Price in USD", example = "429.99")
    private BigDecimal price;

    @ApiModelProperty(value = "Product Status (ACTIVE, INACTIVE, DISCONTINUED)", example = "ACTIVE")
    private String status;

    public UpdateProductRequest() {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
