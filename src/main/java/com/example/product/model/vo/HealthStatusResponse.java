package com.example.product.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Map;

/**
 * Health check & diagnostic status response
 */
@ApiModel(description = "Service health check response")
public class HealthStatusResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "Service Health Status", example = "UP")
    private String status;

    @ApiModelProperty(value = "Microservice name", example = "product-service")
    private String service;

    @ApiModelProperty(value = "Service Version", example = "1.0.0")
    private String version;

    @ApiModelProperty(value = "Current system timestamp")
    private long timestamp;

    @ApiModelProperty(value = "System uptime in seconds", example = "120")
    private long uptimeSeconds;

    @ApiModelProperty(value = "Diagnostic system metrics")
    private Map<String, Object> metrics;

    public HealthStatusResponse() {
    }

    public HealthStatusResponse(String status, String service, String version, long timestamp, long uptimeSeconds, Map<String, Object> metrics) {
        this.status = status;
        this.service = service;
        this.version = version;
        this.timestamp = timestamp;
        this.uptimeSeconds = uptimeSeconds;
        this.metrics = metrics;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getUptimeSeconds() {
        return uptimeSeconds;
    }

    public void setUptimeSeconds(long uptimeSeconds) {
        this.uptimeSeconds = uptimeSeconds;
    }

    public Map<String, Object> getMetrics() {
        return metrics;
    }

    public void setMetrics(Map<String, Object> metrics) {
        this.metrics = metrics;
    }
}
