# ====================================================================
# Production Multi-Stage Dockerfile for Solon Microservice
# ====================================================================

# Stage 1: Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /workspace

# Cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and build production jar
COPY src ./src
RUN mvn clean package -DskipTests -B

# Stage 2: Lightweight runtime stage
FROM eclipse-temurin:21-jre-alpine
LABEL maintainer="engineering@example.com"
WORKDIR /app

# Create non-root user for security best practice
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Copy built artifact from builder stage
COPY --from=builder /workspace/target/solon-product-service.jar app.jar

# Set ownership
RUN chown -R appuser:appgroup /app
USER appuser

# Expose standard microservice port
EXPOSE 8080

# Production JVM flags for container environments
ENV JAVA_OPTS="-XX:+UseG1GC -XX:+UseStringDeduplication -XX:MaxRAMPercentage=75.0 -XX:InitialRAMPercentage=50.0 -Dfile.encoding=UTF-8"

# Healthcheck
HEALTHCHECK --interval=30s --timeout=3s --retries=3 \
  CMD wget --quiet --tries=1 --spider http://localhost:8080/api/v1/health || exit 1

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
