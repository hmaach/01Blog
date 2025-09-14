package com.letsplay.domain.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.letsplay.application.dto.request.CreateProductCommand;
import com.letsplay.application.dto.request.UpdateProductCommand;
import com.letsplay.application.exception.ProductNotFoundException;
import com.letsplay.application.exception.UserNotFoundException;
import com.letsplay.domain.model.Product;
import com.letsplay.domain.model.User;
import com.letsplay.domain.port.in.ProductService;
import com.letsplay.infrastructure.persistence.ProductRepositoryImpl;
import com.letsplay.infrastructure.persistence.UserRepositoryImpl;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private final ProductRepositoryImpl productRepository;
    private final UserRepositoryImpl userRepository;

    public ProductServiceImpl(ProductRepositoryImpl productRepository, UserRepositoryImpl userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Product createProduct(CreateProductCommand cmd, String userId) {

        String id = UUID.randomUUID().toString();

        Product product = new Product(
                id,
                cmd.name(),
                cmd.description(),
                cmd.price(),
                userId,
                LocalDateTime.now()
        );

        productRepository.save(product);

        return product;
    }

    @Override
    public Product findById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id)
                );
        return product;
    }

    @Override
    public List<Product> findByUserId(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return productRepository.findByUserId(user.getId());
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product updateProduct(String productId, UpdateProductCommand cmd, String currentUserId, boolean isAdmin) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        if (!isAdmin && !product.getUserId().equals(currentUserId)) {
            throw new AccessDeniedException("You cannot update this product");
        }

        if (cmd.getName() != null) {
            product.updateName(cmd.getName());
        }

        if (cmd.getDescription() != null) {
            product.updateDescription(cmd.getDescription());
        }

        if (cmd.getPrice() != null) {
            product.updatePrice(cmd.getPrice());
        }

        productRepository.save(product);

        return product;
    }

    @Override
    public void deleteProduct(String productId, String currentUserId, boolean isAdmin) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        if (!isAdmin && !product.getUserId().equals(currentUserId)) {
            throw new AccessDeniedException("You cannot delete this product");
        }

        productRepository.deleteById(productId);
    }

}
