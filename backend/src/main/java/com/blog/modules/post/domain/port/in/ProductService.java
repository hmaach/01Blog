package com.blog.modules.post.domain.port.in;

import java.util.List;

import com.blog.modules.post.domain.model.Product;
import com.blog.modules.post.infrastructure.adapter.in.web.dto.CreateProductCommand;
import com.blog.modules.post.infrastructure.adapter.in.web.dto.UpdateProductCommand;

public interface ProductService {

    List<Product> findAll();

    Product findById(String id);

    List<Product> findByUserId(String id);

    Product createProduct(CreateProductCommand command, String userID);

    Product updateProduct(String productId, UpdateProductCommand cmd, String currentUserId, boolean isAdmin);

    void deleteProduct(String productId, String currentUserId, boolean isAdmin);
}
