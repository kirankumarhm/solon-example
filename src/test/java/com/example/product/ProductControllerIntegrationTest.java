package com.example.product;

import com.example.product.common.ApiResponse;
import com.example.product.common.PageResult;
import com.example.product.controller.HealthController;
import com.example.product.controller.ProductController;
import com.example.product.model.dto.CreateProductRequest;
import com.example.product.model.dto.ProductQueryRequest;
import com.example.product.model.dto.UpdateStockRequest;
import com.example.product.model.vo.HealthStatusResponse;
import com.example.product.model.vo.ProductResponse;
import org.junit.jupiter.api.Test;
import org.noear.solon.annotation.Inject;
import org.noear.solon.test.SolonTest;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SolonTest(ProductApplication.class)
public class ProductControllerIntegrationTest {

    @Inject
    private ProductController productController;

    @Inject
    private HealthController healthController;

    @Test
    public void testHealthEndpoint() {
        ApiResponse<HealthStatusResponse> response = healthController.getHealth();
        assertNotNull(response);
        assertEquals(200, response.getCode());
        assertNotNull(response.getData());
        assertEquals("UP", response.getData().getStatus());
        assertTrue(response.getData().getMetrics().containsKey("jvmTotalMemoryMb"));
        assertTrue(response.getData().getMetrics().containsKey("activeThreads"));
    }

    @Test
    public void testGetProductByIdController() {
        ApiResponse<ProductResponse> response = productController.getProductById(1L);
        assertNotNull(response);
        assertEquals(200, response.getCode());
        assertEquals("PROD-LAPTOP-001", response.getData().getSku());
    }

    @Test
    public void testCreateProductController() {
        CreateProductRequest req = new CreateProductRequest();
        req.setSku("CTRL-TEST-" + UUID.randomUUID().toString().substring(0, 8));
        req.setName("Controller Integration Test Product");
        req.setCategory("Testing");
        req.setPrice(new BigDecimal("99.99"));
        req.setStockQuantity(25);

        ApiResponse<ProductResponse> response = productController.createProduct(req);
        assertNotNull(response);
        assertEquals(200, response.getCode());
        assertEquals("Success", response.getMessage());
        assertNotNull(response.getData().getId());
    }

    @Test
    public void testQueryProductsController() {
        ProductQueryRequest query = new ProductQueryRequest();
        query.setCategory("Electronics");
        ApiResponse<PageResult<ProductResponse>> response = productController.queryProducts(query);
        assertNotNull(response);
        assertEquals(200, response.getCode());
        assertTrue(response.getData().getTotalRow() > 0);
    }

    @Test
    public void testAdjustStockController() {
        UpdateStockRequest stockReq = new UpdateStockRequest(5);
        ApiResponse<ProductResponse> response = productController.updateStock(2L, stockReq);
        assertNotNull(response);
        assertEquals(200, response.getCode());
    }
}
