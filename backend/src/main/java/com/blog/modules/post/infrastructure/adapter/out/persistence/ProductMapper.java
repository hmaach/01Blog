package com.blog.modules.post.infrastructure.adapter.out.persistence;

import com.blog.modules.post.domain.model.Product;

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
