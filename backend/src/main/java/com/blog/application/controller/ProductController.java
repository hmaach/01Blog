package com.blog.application.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.application.dto.request.CreateProductCommand;
import com.blog.application.dto.request.UpdateProductCommand;
import com.blog.application.dto.response.ProductResponse;
import com.blog.domain.model.Product;
import com.blog.domain.service.ProductServiceImpl;
import com.blog.infrastructure.security.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductServiceImpl productService;
    private final JwtService jwtService;

    public ProductController(
            ProductServiceImpl productService,
            JwtService jwtService
    ) {
        this.productService = productService;
        this.jwtService = jwtService;

    }

    @GetMapping
    public List<ProductResponse> getProducts() {
        return productService.findAll().stream()
                .map(ProductResponse::fromDomain)
                .toList();
    }

    @GetMapping("/user")
    public List<ProductResponse> getProductsOfCurrentUser(HttpServletRequest request) {
        String id = jwtService.extractUserIdFromRequest(request);

        return productService.findByUserId(id).stream()
                .map(ProductResponse::fromDomain)
                .toList();
    }

    @GetMapping("/user/{id}")
    public List<ProductResponse> getProductsByUserId(@PathVariable String id) {
        return productService.findByUserId(id).stream()
                .map(ProductResponse::fromDomain)
                .toList();
    }

    @GetMapping("/{id}")
    public ProductResponse getProduct(@PathVariable String id) {
        return ProductResponse.fromDomain(productService.findById(id));
    }

    @PostMapping
    public ProductResponse createProduct(@RequestBody @Valid CreateProductCommand cmd, HttpServletRequest request) {
        String userId = jwtService.extractUserIdFromRequest(request);
        return ProductResponse.fromDomain(productService.createProduct(cmd, userId));
    }

    @PatchMapping("/{id}")
    public ProductResponse updateProduct(
            @PathVariable String id,
            @RequestBody UpdateProductCommand cmd,
            HttpServletRequest request) {

        String userId = jwtService.extractUserIdFromRequest(request);
        String role = jwtService.extractRoleFromRequest(request);
        boolean isAdmin = role.equals("ADMIN");

        Product updated = productService.updateProduct(id, cmd, userId, isAdmin);
        return ProductResponse.fromDomain(updated);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable String id, HttpServletRequest request) {

        String userId = jwtService.extractUserIdFromRequest(request);
        String role = jwtService.extractUserIdFromRequest(request);
        boolean isAdmin = role.equals("ADMIN");

        System.err.println("---------------------" + role + "---------------------");

        productService.deleteProduct(id, userId, isAdmin);
    }
}
