package com.example.product.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Standard Production-Grade API Response Envelope
 *
 * @param <T> Payload type
 */
@ApiModel(description = "Standard API response envelope")
public class ApiResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "Business status code (200 = Success)", example = "200")
    private int code;

    @ApiModelProperty(value = "Human-readable status or error message", example = "Success")
    private String message;

    @ApiModelProperty(value = "Response payload")
    private T data;

    @ApiModelProperty(value = "Response timestamp in epoch milliseconds", example = "1728643200000")
    private long timestamp;

    @ApiModelProperty(value = "Distributed tracing correlation identifier", example = "4a18a5137cd54497a9f7e81057e96bfa")
    private String traceId;

    public ApiResponse() {
        this.timestamp = System.currentTimeMillis();
        this.traceId = TraceHolder.get();
    }

    public ApiResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
        this.traceId = TraceHolder.get();
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMessage(), data);
    }

    public static <T> ApiResponse<T> ok() {
        return ok(null);
    }

    public static <T> ApiResponse<T> fail(int code, String message) {
        return new ApiResponse<>(code, message, null);
    }

    public static <T> ApiResponse<T> fail(ErrorCode errorCode) {
        return new ApiResponse<>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    public static <T> ApiResponse<T> fail(ErrorCode errorCode, String customMessage) {
        return new ApiResponse<>(errorCode.getCode(), customMessage, null);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }
}
