package com.example.product.mapper;

import com.example.product.model.entity.Product;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * MyBatis-Flex Data Access Mapper for Product entity
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {
}
