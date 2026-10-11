package com.example.product;

import com.example.product.common.ErrorCode;
import com.example.product.common.PageResult;
import com.example.product.exception.BusinessException;
import com.example.product.exception.ResourceNotFoundException;
import com.example.product.exception.ValidationException;
import com.example.product.model.dto.CreateProductRequest;
import com.example.product.model.dto.ProductQueryRequest;
import com.example.product.model.dto.UpdateProductRequest;
import com.example.product.model.vo.ProductResponse;
import com.example.product.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.noear.solon.annotation.Inject;
import org.noear.solon.test.SolonTest;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SolonTest(ProductApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductServiceTest {

    @Inject
    private ProductService productService;

    @Test
    @Order(1)
    public void testGetInitialSeedProduct() {
        ProductResponse product = productService.getProductById(1L);
        assertNotNull(product);
        assertEquals("PROD-LAPTOP-001", product.getSku());
        assertEquals("Electronics", product.getCategory());
        assertEquals(45, product.getStockQuantity());
    }

    @Test
    @Order(2)
    public void testCreateProductSuccess() {
        String uniqueSku = "TEST-SKU-" + UUID.randomUUID().toString().substring(0, 8);
        CreateProductRequest req = new CreateProductRequest();
        req.setSku(uniqueSku);
        req.setName("Test Smart Watch");
        req.setDescription("Water resistant fitness tracker");
        req.setCategory("Wearables");
        req.setPrice(new BigDecimal("199.99"));
        req.setStockQuantity(30);
        req.setStatus("ACTIVE");

        ProductResponse created = productService.createProduct(req);
        assertNotNull(created);
        assertNotNull(created.getId());
        assertEquals(uniqueSku, created.getSku());
        assertEquals("Test Smart Watch", created.getName());
        assertEquals(30, created.getStockQuantity());

        // Verify retrieval by SKU
        ProductResponse fetched = productService.getProductBySku(uniqueSku);
        assertEquals(created.getId(), fetched.getId());
    }

    @Test
    @Order(3)
    public void testCreateProductDuplicateSkuThrowsException() {
        CreateProductRequest req = new CreateProductRequest();
        req.setSku("PROD-LAPTOP-001"); // already in seed data
        req.setName("Duplicate Laptop");
        req.setCategory("Electronics");
        req.setPrice(new BigDecimal("999.00"));
        req.setStockQuantity(10);

        BusinessException ex = assertThrows(BusinessException.class, () -> productService.createProduct(req));
        assertEquals(ErrorCode.DUPLICATE_RESOURCE, ex.getErrorCode());
    }

    @Test
    @Order(4)
    public void testCreateProductNegativePriceValidation() {
        CreateProductRequest req = new CreateProductRequest();
        req.setSku("TEST-NEG-PRICE");
        req.setName("Invalid Price Product");
        req.setCategory("Test");
        req.setPrice(new BigDecimal("-10.00"));
        req.setStockQuantity(5);

        assertThrows(ValidationException.class, () -> productService.createProduct(req));
    }

    @Test
    @Order(5)
    public void testGetProductNotFoundThrowsException() {
        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(999999L));
    }

    @Test
    @Order(6)
    public void testQueryProductsWithFilters() {
        ProductQueryRequest query = new ProductQueryRequest();
        query.setCategory("Electronics");
        query.setPage(1);
        query.setSize(5);

        PageResult<ProductResponse> result = productService.queryProducts(query);
        assertNotNull(result);
        assertTrue(result.getTotalRow() >= 2);
        assertFalse(result.getRecords().isEmpty());
    }

    @Test
    @Order(7)
    public void testUpdateProductDetails() {
        UpdateProductRequest updateReq = new UpdateProductRequest();
        updateReq.setName("Developer Pro Laptop 16-inch (Updated Edition)");
        updateReq.setPrice(new BigDecimal("1799.00"));

        ProductResponse updated = productService.updateProduct(1L, updateReq);
        assertEquals("Developer Pro Laptop 16-inch (Updated Edition)", updated.getName());
        assertEquals(new BigDecimal("1799.00"), updated.getPrice());
    }

    @Test
    @Order(8)
    public void testUpdateStockIncrementAndDecrement() {
        // Current seed stock is 45, add 10 -> 55
        ProductResponse inc = productService.updateStock(1L, 10);
        assertEquals(55, inc.getStockQuantity());

        // Deduct 20 -> 35
        ProductResponse dec = productService.updateStock(1L, -20);
        assertEquals(35, dec.getStockQuantity());

        // Attempt to deduct 100 -> should fail with Insufficient Stock
        BusinessException ex = assertThrows(BusinessException.class, () -> productService.updateStock(1L, -100));
        assertEquals(ErrorCode.INSUFFICIENT_STOCK, ex.getErrorCode());
    }

    @Test
    @Order(9)
    public void testDeleteProduct() {
        String sku = "TEMP-DEL-" + UUID.randomUUID().toString().substring(0, 8);
        CreateProductRequest req = new CreateProductRequest();
        req.setSku(sku);
        req.setName("Temporary Product");
        req.setCategory("Temp");
        req.setPrice(new BigDecimal("10.00"));
        req.setStockQuantity(1);

        ProductResponse created = productService.createProduct(req);
        Long id = created.getId();

        productService.deleteProduct(id);

        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(id));
    }
}
