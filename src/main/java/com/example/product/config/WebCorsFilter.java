package com.example.product.config;

import org.noear.solon.annotation.Component;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Filter;
import org.noear.solon.core.handle.FilterChain;

/**
 * Global Cross-Origin Resource Sharing (CORS) Filter.
 * Allows frontend Single Page Apps and Swagger UI to interact smoothly with the APIs.
 */
@Component(index = -200)
public class WebCorsFilter implements Filter {

    @Override
    public void doFilter(Context ctx, FilterChain chain) throws Throwable {
        ctx.headerSet("Access-Control-Allow-Origin", "*");
        ctx.headerSet("Access-Control-Allow-Methods", "GET, POST, PUT, PATCH, DELETE, OPTIONS");
        ctx.headerSet("Access-Control-Allow-Headers", "Content-Type, Authorization, X-Requested-With, X-Trace-Id");
        ctx.headerSet("Access-Control-Expose-Headers", "X-Trace-Id");
        ctx.headerSet("Access-Control-Max-Age", "3600");

        if ("OPTIONS".equalsIgnoreCase(ctx.method())) {
            ctx.status(200);
            return;
        }

        chain.doFilter(ctx);
    }
}
