package com.blog.modules.post.infrastructure.adapter.in.web.dto;

import java.time.LocalDateTime;

import com.blog.modules.post.domain.model.Product;

public record ProductResponse(
        String id,
        String name,
        String description,
        Double price,
        String userId,
        LocalDateTime createdAt
        ) {

    public static ProductResponse fromDomain(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getUserId(),
                product.getCreatedAt()
        );
    }
}
