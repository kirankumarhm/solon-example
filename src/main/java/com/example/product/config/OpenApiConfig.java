package com.example.product.config;

import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Configuration;
import org.noear.solon.docs.DocDocket;
import org.noear.solon.docs.models.ApiContact;
import org.noear.solon.docs.models.ApiInfo;

/**
 * OpenAPI 2 / Knife4j Swagger Documentation Configuration.
 * Access interactive documentation UI at: http://localhost:8080/doc.html
 */
@Configuration
public class OpenApiConfig {

    @Bean("productApi")
    public DocDocket productApi() {
        return new DocDocket()
                .groupName("Product Service API")
                .info(new ApiInfo()
                        .title("Product Catalog Microservice API")
                        .description("Production-Grade Solon Framework Microservice RESTful APIs with Swagger/Knife4j, MyBatis-Flex, and H2 database.")
                        .version("1.0.0")
                        .contact(new ApiContact()
                                .name("Engineering Architecture Team")
                                .email("engineering@example.com")
                                .url("https://example.com/docs")))
                .apis("com.example.product.controller");
    }
}
