package com.example.product.filter;

import com.example.product.common.ApiResponse;
import com.example.product.common.ErrorCode;
import com.example.product.exception.BusinessException;
import com.example.product.exception.ResourceNotFoundException;
import com.example.product.exception.ValidationException;
import org.noear.solon.annotation.Component;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Filter;
import org.noear.solon.core.handle.FilterChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Global Exception Handling Filter for Solon Web Layer.
 * Catches all runtime and business exceptions, logs them with correlation traceId,
 * and renders a unified ApiResponse with standard HTTP status code.
 */
@Component(index = 0)
public class GlobalExceptionFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionFilter.class);

    @Override
    public void doFilter(Context ctx, FilterChain chain) throws Throwable {
        try {
            chain.doFilter(ctx);
        } catch (BusinessException e) {
            log.warn("Business rule violation: [{}] {}", e.getErrorCode().getCode(), e.getMessage());
            int httpStatus = e.getErrorCode().getHttpStatus();
            ctx.status(httpStatus);
            ctx.render(ApiResponse.fail(e.getErrorCode(), e.getMessage()));
        } catch (ResourceNotFoundException e) {
            log.warn("Resource not found: {}", e.getMessage());
            ctx.status(404);
            ctx.render(ApiResponse.fail(ErrorCode.NOT_FOUND, e.getMessage()));
        } catch (ValidationException e) {
            log.warn("Validation error: {}", e.getMessage());
            ctx.status(400);
            ctx.render(ApiResponse.fail(ErrorCode.BAD_REQUEST, e.getMessage()));
        } catch (IllegalArgumentException e) {
            log.warn("Illegal argument: {}", e.getMessage());
            ctx.status(400);
            ctx.render(ApiResponse.fail(ErrorCode.BAD_REQUEST, e.getMessage()));
        } catch (org.noear.solon.core.exception.StatusException e) {
            ctx.status(e.getCode());
            if (e.getCode() == 404) {
                ctx.render(ApiResponse.fail(ErrorCode.NOT_FOUND.getCode(), e.getMessage()));
            } else {
                ctx.render(ApiResponse.fail(e.getCode(), e.getMessage()));
            }
        } catch (Throwable e) {
            log.error("Unhandled unexpected exception during request processing: ", e);
            ctx.status(500);
            ctx.render(ApiResponse.fail(ErrorCode.INTERNAL_SERVER_ERROR, "An internal error occurred. Please try again later."));
        }
    }
}
