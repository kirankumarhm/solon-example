package com.example.product.filter;

import org.noear.solon.annotation.Component;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Filter;
import org.noear.solon.core.handle.FilterChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Filter that logs incoming HTTP requests with latency, client IP, and status code.
 */
@Component(index = -50)
public class RequestLoggingFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public void doFilter(Context ctx, FilterChain chain) throws Throwable {
        String path = ctx.path();

        // Skip verbose logging for static documentation assets
        if (path.startsWith("/doc.html") || path.startsWith("/webjars") || path.startsWith("/swagger")) {
            chain.doFilter(ctx);
            return;
        }

        long startTime = System.currentTimeMillis();
        String method = ctx.method();
        String ip = ctx.realIp();

        log.info("Incoming Request -> [{}] {} from IP: {}", method, path, ip);

        try {
            chain.doFilter(ctx);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            int status = ctx.status();
            log.info("Completed Request <- [{}] {} returned status: {} (duration: {}ms)", method, path, status, duration);
        }
    }
}
