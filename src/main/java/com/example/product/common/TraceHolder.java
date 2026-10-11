package com.example.product.common;

/**
 * Thread-local store for distributed tracing correlation ID
 */
public final class TraceHolder {

    private static final ThreadLocal<String> TRACE_ID_HOLDER = new ThreadLocal<>();

    private TraceHolder() {
    }

    public static void set(String traceId) {
        TRACE_ID_HOLDER.set(traceId);
    }

    public static String get() {
        return TRACE_ID_HOLDER.get();
    }

    public static void clear() {
        TRACE_ID_HOLDER.remove();
    }
}
