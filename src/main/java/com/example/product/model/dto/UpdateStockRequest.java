package com.example.product.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.noear.solon.validation.annotation.NotNull;

import java.io.Serializable;

/**
 * Payload for updating/adjusting product stock
 */
@ApiModel(description = "Payload for adjusting product inventory stock")
public class UpdateStockRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "Stock delta change (positive for restocking, negative for order deduction)", required = true, example = "10")
    @NotNull(message = "Stock delta must not be null")
    private Integer delta;

    public UpdateStockRequest() {
    }

    public UpdateStockRequest(Integer delta) {
        this.delta = delta;
    }

    public Integer getDelta() {
        return delta;
    }

    public void setDelta(Integer delta) {
        this.delta = delta;
    }
}
