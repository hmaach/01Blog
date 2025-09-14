package com.letsplay.infrastructure.persistence.mapper;

import com.letsplay.domain.model.Product;
import com.letsplay.infrastructure.persistence.entity.ProductEntity;

public class ProductMapper {

    public static ProductEntity toEntity(Product product) {
        if (product == null) {
            return null;
        }

        return new ProductEntity(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getUserId(),
                product.getCreatedAt()
        );
    }

    public static Product toDomain(ProductEntity entity) {
        if (entity == null) {
            return null;
        }

        return new Product(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getUserId(),
                entity.getCreatedAt()
        );
    }
}
