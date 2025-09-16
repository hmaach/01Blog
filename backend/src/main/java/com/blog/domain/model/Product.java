package com.blog.domain.model;

import java.time.LocalDateTime;

public class Product {

    private final String id;
    private final String userId;
    private String name;
    private String description;
    private Double price;
    private final LocalDateTime createdAt;

    public Product(
            String id,
            String name,
            String description,
            Double price,
            String userId,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.userId = userId;
        this.createdAt = createdAt;
    }

    // --- Getters ---
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public String getUserId() {
        return userId;
    }

    // --- Domain actions ---
    public void updateName(String name) {
        this.name = name;
    }

    public void updateDescription(String description) {
        this.description = description;
    }

    public void updatePrice(Double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }

        this.price = price;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
