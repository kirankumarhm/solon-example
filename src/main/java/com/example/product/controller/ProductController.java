package com.example.product.controller;

import com.example.product.common.ApiResponse;
import com.example.product.common.PageResult;
import com.example.product.model.dto.CreateProductRequest;
import com.example.product.model.dto.ProductQueryRequest;
import com.example.product.model.dto.UpdateProductRequest;
import com.example.product.model.dto.UpdateStockRequest;
import com.example.product.model.vo.ProductResponse;
import com.example.product.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.noear.solon.annotation.Body;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Delete;
import org.noear.solon.annotation.Get;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.annotation.Patch;
import org.noear.solon.annotation.Path;
import org.noear.solon.annotation.Post;
import org.noear.solon.annotation.Put;
import org.noear.solon.validation.annotation.Validated;

/**
 * Production-Grade REST Controller for Product Catalog Operations
 */
@Api(tags = "Product Catalog Management")
@Controller
@Mapping("/api/v1/products")
public class ProductController {

    @Inject
    private ProductService productService;

    @ApiOperation(value = "Create Product", notes = "Creates a new catalog product with uniqueness validation on SKU")
    @Post
    @Mapping
    public ApiResponse<ProductResponse> createProduct(@Validated @Body CreateProductRequest request) {
        ProductResponse response = productService.createProduct(request);
        return ApiResponse.ok(response);
    }

    @ApiOperation(value = "Get Product by ID", notes = "Retrieves a single product by its database primary key")
    @Get
    @Mapping("/{id}")
    public ApiResponse<ProductResponse> getProductById(@ApiParam(value = "Product Primary ID", required = true) @Path("id") Long id) {
        ProductResponse response = productService.getProductById(id);
        return ApiResponse.ok(response);
    }

    @ApiOperation(value = "Get Product by SKU", notes = "Retrieves a single product by its unique SKU code")
    @Get
    @Mapping("/sku/{sku}")
    public ApiResponse<ProductResponse> getProductBySku(@ApiParam(value = "Product SKU Code", required = true) @Path("sku") String sku) {
        ProductResponse response = productService.getProductBySku(sku);
        return ApiResponse.ok(response);
    }

    @ApiOperation(value = "Search Products", notes = "Dynamic query with multi-field search and pagination")
    @Get
    @Mapping
    public ApiResponse<PageResult<ProductResponse>> queryProducts(ProductQueryRequest query) {
        PageResult<ProductResponse> result = productService.queryProducts(query);
        return ApiResponse.ok(result);
    }

    @ApiOperation(value = "Update Product", notes = "Updates an existing product's details")
    @Put
    @Mapping("/{id}")
    public ApiResponse<ProductResponse> updateProduct(
            @ApiParam(value = "Product Primary ID", required = true) @Path("id") Long id,
            @Body UpdateProductRequest request) {
        ProductResponse response = productService.updateProduct(id, request);
        return ApiResponse.ok(response);
    }

    @ApiOperation(value = "Adjust Inventory Stock", notes = "Atomically adjusts stock quantity (positive delta adds stock, negative delta decrements)")
    @Patch
    @Mapping("/{id}/stock")
    public ApiResponse<ProductResponse> updateStock(
            @ApiParam(value = "Product Primary ID", required = true) @Path("id") Long id,
            @Validated @Body UpdateStockRequest request) {
        ProductResponse response = productService.updateStock(id, request.getDelta());
        return ApiResponse.ok(response);
    }

    @ApiOperation(value = "Delete Product", notes = "Deletes a product by ID")
    @Delete
    @Mapping("/{id}")
    public ApiResponse<Void> deleteProduct(@ApiParam(value = "Product Primary ID", required = true) @Path("id") Long id) {
        productService.deleteProduct(id);
        return ApiResponse.ok();
    }
}
