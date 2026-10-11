package com.example.product.filter;

import com.example.product.common.TraceHolder;
import org.noear.solon.annotation.Component;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Filter;
import org.noear.solon.core.handle.FilterChain;
import org.slf4j.MDC;

import java.util.UUID;

/**
 * Filter that establishes distributed trace context for every request.
 * Extracts 'X-Trace-Id' from incoming headers or generates a new UUID.
 * Populates SLF4J MDC and sets the response header.
 */
@Component(index = -100)
public class TraceIdFilter implements Filter {

    public static final String TRACE_HEADER = "X-Trace-Id";
    public static final String MDC_KEY = "traceId";

    @Override
    public void doFilter(Context ctx, FilterChain chain) throws Throwable {
        String traceId = ctx.header(TRACE_HEADER);
        if (traceId == null || traceId.trim().isEmpty()) {
            traceId = UUID.randomUUID().toString().replace("-", "");
        }

        MDC.put(MDC_KEY, traceId);
        TraceHolder.set(traceId);
        ctx.headerSet(TRACE_HEADER, traceId);

        try {
            chain.doFilter(ctx);
        } finally {
            MDC.remove(MDC_KEY);
            TraceHolder.clear();
        }
    }
}
