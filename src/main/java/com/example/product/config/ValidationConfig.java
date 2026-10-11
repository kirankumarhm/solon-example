package com.example.product.config;

import com.example.product.common.ApiResponse;
import com.example.product.common.ErrorCode;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Init;
import org.noear.solon.validation.ValidatorManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Global Validation Failure Configuration.
 * Intercepts Solon validation annotations failures and renders standard ApiResponse.
 */
@Component
public class ValidationConfig {

    private static final Logger log = LoggerFactory.getLogger(ValidationConfig.class);

    @Init
    public void init() {
        ValidatorManager.setFailureHandler((ctx, ano, result, message) -> {
            log.warn("Validation constraint violated: {}", message);
            ctx.status(400);
            ctx.render(ApiResponse.fail(ErrorCode.BAD_REQUEST, message));
            return false; // Stop further validation processing
        });
    }
}
