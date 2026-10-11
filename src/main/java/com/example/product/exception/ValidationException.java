package com.example.product.exception;

import com.example.product.common.ErrorCode;

/**
 * Exception thrown when input validation fails (maps to HTTP 400)
 */
public class ValidationException extends BaseException {

    public ValidationException(String message) {
        super(ErrorCode.BAD_REQUEST, message);
    }
}
