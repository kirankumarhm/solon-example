package com.example.product.exception;

import com.example.product.common.ErrorCode;

/**
 * Exception thrown when business validation or domain constraint fails
 */
public class BusinessException extends BaseException {

    public BusinessException(ErrorCode errorCode) {
        super(errorCode, errorCode.getMessage());
    }

    public BusinessException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
