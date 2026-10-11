package com.example.product.controller;

import com.example.product.common.ApiResponse;
import com.example.product.model.vo.HealthStatusResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.noear.solon.Solon;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Get;
import org.noear.solon.annotation.Mapping;

import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;

/**
 * Production Health Check & Diagnostic Metrics Controller
 */
@Api(tags = "System Health & Diagnostics")
@Controller
@Mapping("/api/v1/health")
public class HealthController {

    private final long startupTime = System.currentTimeMillis();

    @ApiOperation(value = "Service Health Check", notes = "Returns runtime status, JVM metrics, and uptime")
    @Get
    @Mapping
    public ApiResponse<HealthStatusResponse> getHealth() {
        Runtime runtime = Runtime.getRuntime();
        long uptime = (System.currentTimeMillis() - startupTime) / 1000;

        Map<String, Object> metrics = new HashMap<>();
        metrics.put("jvmTotalMemoryMb", runtime.totalMemory() / (1024 * 1024));
        metrics.put("jvmFreeMemoryMb", runtime.freeMemory() / (1024 * 1024));
        metrics.put("jvmMaxMemoryMb", runtime.maxMemory() / (1024 * 1024));
        metrics.put("jvmUsedMemoryMb", (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024));
        metrics.put("availableProcessors", runtime.availableProcessors());
        metrics.put("activeThreads", Thread.activeCount());
        metrics.put("jvmUptimeMs", ManagementFactory.getRuntimeMXBean().getUptime());
        metrics.put("environment", Solon.cfg().env());

        HealthStatusResponse response = new HealthStatusResponse(
                "UP",
                Solon.cfg().appName(),
                "1.0.0",
                System.currentTimeMillis(),
                uptime,
                metrics
        );

        return ApiResponse.ok(response);
    }
}
