package com.letsplay.domain.port.in;

import java.util.List;

import com.letsplay.application.dto.request.CreateProductCommand;
import com.letsplay.application.dto.request.UpdateProductCommand;
import com.letsplay.domain.model.Product;

public interface ProductService {

    List<Product> findAll();

    Product findById(String id);

    List<Product> findByUserId(String id);

    Product createProduct(CreateProductCommand command, String userID);

    Product updateProduct(String productId, UpdateProductCommand cmd, String currentUserId, boolean isAdmin);

    void deleteProduct(String productId, String currentUserId, boolean isAdmin);
}
