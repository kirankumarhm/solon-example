package com.example.product.common;

/**
 * Standardized API Error and Status Codes
 */
public enum ErrorCode {

    SUCCESS(200, "Success", 200),
    BAD_REQUEST(40001, "Invalid request parameters", 400),
    NOT_FOUND(40401, "Resource not found", 404),
    DUPLICATE_RESOURCE(40901, "Resource with specified identifier already exists", 409),
    INSUFFICIENT_STOCK(42201, "Insufficient stock for requested operation", 422),
    INVALID_PRODUCT_STATUS(42202, "Invalid product status transition", 422),
    INTERNAL_SERVER_ERROR(50001, "Internal server error occurred", 500);

    private final int code;
    private final String message;
    private final int httpStatus;

    ErrorCode(int code, String message, int httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public int getHttpStatus() {
        return httpStatus;
    }
}
