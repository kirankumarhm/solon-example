package com.example.product.service;

import com.example.product.common.PageResult;
import com.example.product.model.dto.CreateProductRequest;
import com.example.product.model.dto.ProductQueryRequest;
import com.example.product.model.dto.UpdateProductRequest;
import com.example.product.model.vo.ProductResponse;

/**
 * Service contract for product catalog operations
 */
public interface ProductService {

    /**
     * Create a new product with uniqueness validation
     */
    ProductResponse createProduct(CreateProductRequest request);

    /**
     * Retrieve product by database primary key ID
     */
    ProductResponse getProductById(Long id);

    /**
     * Retrieve product by unique SKU
     */
    ProductResponse getProductBySku(String sku);

    /**
     * Query products with dynamic filtering and pagination
     */
    PageResult<ProductResponse> queryProducts(ProductQueryRequest query);

    /**
     * Update product details
     */
    ProductResponse updateProduct(Long id, UpdateProductRequest request);

    /**
     * Adjust product inventory stock (increment or decrement)
     */
    ProductResponse updateStock(Long id, int quantityDelta);

    /**
     * Delete product by ID
     */
    void deleteProduct(Long id);
}
