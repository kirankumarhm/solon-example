package com.example.product;

import org.noear.solon.Solon;
import org.noear.solon.annotation.SolonMain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main application entry point for the Solon Product Catalog Microservice.
 */
@SolonMain
public class ProductApplication {

    private static final Logger log = LoggerFactory.getLogger(ProductApplication.class);

    public static void main(String[] args) {
        Solon.start(ProductApplication.class, args, app -> {
            // Map Knife4j Swagger UI resources from META-INF/resources (doc.html, webjars, etc.)
            org.noear.solon.web.staticfiles.StaticMappings.add("/",
                    new org.noear.solon.web.staticfiles.repository.ClassPathStaticRepository("META-INF/resources"));

            int port = Solon.cfg().serverPort();
            log.info("====================================================================");
            log.info("Solon Product Catalog Microservice started successfully!");
            log.info("Server Port:        {}", port);
            log.info("Swagger/Knife4j UI: http://localhost:{}/doc.html", port);
            log.info("Health Check API:   http://localhost:{}/api/v1/health", port);
            log.info("Products API:       http://localhost:{}/api/v1/products", port);
            log.info("====================================================================");
        });
    }
}
