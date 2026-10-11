package com.example.product.exception;

import com.example.product.common.ErrorCode;

/**
 * Exception thrown when requested entity is not found (maps to HTTP 404)
 */
public class ResourceNotFoundException extends BaseException {

    public ResourceNotFoundException(String resourceName, Object identifier) {
        super(ErrorCode.NOT_FOUND, String.format("%s with identifier '%s' was not found", resourceName, identifier));
    }

    public ResourceNotFoundException(String message) {
        super(ErrorCode.NOT_FOUND, message);
    }
}
