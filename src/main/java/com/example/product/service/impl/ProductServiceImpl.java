package com.example.product.service.impl;

import com.example.product.common.ErrorCode;
import com.example.product.common.PageResult;
import com.example.product.exception.BusinessException;
import com.example.product.exception.ResourceNotFoundException;
import com.example.product.exception.ValidationException;
import com.example.product.mapper.ProductMapper;
import com.example.product.model.dto.CreateProductRequest;
import com.example.product.model.dto.ProductQueryRequest;
import com.example.product.model.dto.UpdateProductRequest;
import com.example.product.model.entity.Product;
import com.example.product.model.vo.ProductResponse;
import com.example.product.service.ProductService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.data.annotation.Tran;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Production-grade implementation of ProductService
 */
@Component
public class ProductServiceImpl implements ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Inject
    private ProductMapper productMapper;

    @Override
    @Tran
    public ProductResponse createProduct(CreateProductRequest request) {
        log.info("Creating product with SKU: {}", request.getSku());

        // Validate business invariants
        if (request.getPrice() == null || request.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("Product price cannot be negative");
        }
        if (request.getStockQuantity() == null || request.getStockQuantity() < 0) {
            throw new ValidationException("Initial stock quantity cannot be negative");
        }

        // Validate uniqueness of SKU
        QueryWrapper skuCheck = QueryWrapper.create().where("sku = ?", request.getSku().trim());
        Product existing = productMapper.selectOneByQuery(skuCheck);
        if (existing != null) {
            throw new BusinessException(ErrorCode.DUPLICATE_RESOURCE,
                    "Product with SKU '" + request.getSku() + "' already exists");
        }

        Product product = new Product();
        product.setSku(request.getSku().trim());
        product.setName(request.getName().trim());
        product.setDescription(request.getDescription());
        product.setCategory(request.getCategory().trim());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setStatus(request.getStatus() != null ? request.getStatus().trim().toUpperCase() : "ACTIVE");
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        productMapper.insert(product);
        log.info("Successfully created product id: {} with SKU: {}", product.getId(), product.getSku());

        return ProductResponse.fromEntity(product);
    }

    @Override
    public ProductResponse getProductById(Long id) {
        if (id == null || id <= 0) {
            throw new ValidationException("Invalid product ID: " + id);
        }
        Product product = productMapper.selectOneById(id);
        if (product == null) {
            throw new ResourceNotFoundException("Product", id);
        }
        return ProductResponse.fromEntity(product);
    }

    @Override
    public ProductResponse getProductBySku(String sku) {
        if (sku == null || sku.trim().isEmpty()) {
            throw new ValidationException("SKU parameter must not be empty");
        }
        QueryWrapper query = QueryWrapper.create().where("sku = ?", sku.trim());
        Product product = productMapper.selectOneByQuery(query);
        if (product == null) {
            throw new ResourceNotFoundException("Product with SKU", sku);
        }
        return ProductResponse.fromEntity(product);
    }

    @Override
    public PageResult<ProductResponse> queryProducts(ProductQueryRequest query) {
        QueryWrapper wrapper = QueryWrapper.create();

        if (query.getKeyword() != null && !query.getKeyword().trim().isEmpty()) {
            String kw = "%" + query.getKeyword().trim() + "%";
            wrapper.and("(name LIKE ? OR sku LIKE ? OR description LIKE ?)", kw, kw, kw);
        }

        if (query.getCategory() != null && !query.getCategory().trim().isEmpty()) {
            wrapper.and("category = ?", query.getCategory().trim());
        }

        if (query.getStatus() != null && !query.getStatus().trim().isEmpty()) {
            wrapper.and("status = ?", query.getStatus().trim().toUpperCase());
        }

        if (query.getMinPrice() != null) {
            wrapper.and("price >= ?", query.getMinPrice());
        }

        if (query.getMaxPrice() != null) {
            wrapper.and("price <= ?", query.getMaxPrice());
        }

        wrapper.orderBy("id", false);

        Page<Product> page = productMapper.paginate(Page.of(query.getPage(), query.getSize()), wrapper);
        return PageResult.from(page, ProductResponse::fromEntity);
    }

    @Override
    @Tran
    public ProductResponse updateProduct(Long id, UpdateProductRequest request) {
        log.info("Updating product id: {}", id);
        Product product = productMapper.selectOneById(id);
        if (product == null) {
            throw new ResourceNotFoundException("Product", id);
        }

        if (request.getName() != null && !request.getName().trim().isEmpty()) {
            product.setName(request.getName().trim());
        }
        if (request.getDescription() != null) {
            product.setDescription(request.getDescription());
        }
        if (request.getCategory() != null && !request.getCategory().trim().isEmpty()) {
            product.setCategory(request.getCategory().trim());
        }
        if (request.getPrice() != null) {
            if (request.getPrice().compareTo(BigDecimal.ZERO) < 0) {
                throw new ValidationException("Price cannot be negative");
            }
            product.setPrice(request.getPrice());
        }
        if (request.getStatus() != null && !request.getStatus().trim().isEmpty()) {
            product.setStatus(request.getStatus().trim().toUpperCase());
        }

        product.setUpdatedAt(LocalDateTime.now());
        productMapper.update(product);
        log.info("Successfully updated product id: {}", id);

        return ProductResponse.fromEntity(product);
    }

    @Override
    @Tran
    public ProductResponse updateStock(Long id, int quantityDelta) {
        log.info("Adjusting stock for product id: {} by delta: {}", id, quantityDelta);
        Product product = productMapper.selectOneById(id);
        if (product == null) {
            throw new ResourceNotFoundException("Product", id);
        }

        int currentStock = product.getStockQuantity() != null ? product.getStockQuantity() : 0;
        int newStock = currentStock + quantityDelta;
        if (newStock < 0) {
            throw new BusinessException(ErrorCode.INSUFFICIENT_STOCK,
                    String.format("Insufficient stock for product '%s'. Current stock: %d, delta: %d",
                            product.getName(), currentStock, quantityDelta));
        }

        product.setStockQuantity(newStock);
        product.setUpdatedAt(LocalDateTime.now());
        productMapper.update(product);

        log.info("Stock updated for product id: {}. Old: {}, New: {}", id, currentStock, newStock);
        return ProductResponse.fromEntity(product);
    }

    @Override
    @Tran
    public void deleteProduct(Long id) {
        log.info("Deleting product id: {}", id);
        Product product = productMapper.selectOneById(id);
        if (product == null) {
            throw new ResourceNotFoundException("Product", id);
        }
        productMapper.deleteById(id);
        log.info("Successfully deleted product id: {}", id);
    }
}
