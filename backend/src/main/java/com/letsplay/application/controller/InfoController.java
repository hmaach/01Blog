package com.letsplay.application.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {

    @GetMapping("/")
    public Map<String, Object> getInfo() {
        return Map.of(
                "project", "LET'S PLAY - Java backend with Hexagonal Architecture, MongoDB, and Docker",
                "author", "Hamza Maach",
                "github", "https://github.com/hmaach/lets-play",
                "endpoints", List.of(
                        Map.of("method", "POST", "path", "/api/auth/register", "description", "Register a new user"),
                        Map.of("method", "POST", "path", "/api/auth/login", "description", "Login a user"),
                        Map.of("method", "GET", "path", "/api/admin/users", "description", "List all users (Admin)"),
                        Map.of("method", "GET", "path", "/api/admin/users/{id}", "description", "Get user by ID (Admin)"),
                        Map.of("method", "POST", "path", "/api/admin/users", "description", "Create a user (Admin)"),
                        Map.of("method", "PATCH", "path", "/api/admin/users/{id}", "description", "Update user (Admin)"),
                        Map.of("method", "DELETE", "path", "/api/admin/users/{id}", "description", "Delete user (Admin)"),
                        Map.of("method", "GET", "path", "/api/me", "description", "Get current user profile"),
                        Map.of("method", "PATCH", "path", "/api/me", "description", "Update current user"),
                        Map.of("method", "DELETE", "path", "/api/me", "description", "Delete current user"),
                        Map.of("method", "GET", "path", "/api/products", "description", "List all products"),
                        Map.of("method", "GET", "path", "/api/products/{id}", "description", "Get product by ID"),
                        Map.of("method", "GET", "path", "/api/products/user", "description", "Get products of current user"),
                        Map.of("method", "GET", "path", "/api/products/user/{id}", "description", "Get products by user ID"),
                        Map.of("method", "POST", "path", "/api/products", "description", "Create a new product (Admin)"),
                        Map.of("method", "PATCH", "path", "/api/products/{id}", "description", "Update product (Admin)"),
                        Map.of("method", "DELETE", "path", "/api/products/{id}", "description", "Delete product (Admin)")
                )
        );
    }
}
