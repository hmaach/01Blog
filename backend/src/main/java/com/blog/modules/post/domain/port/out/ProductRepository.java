package com.blog.modules.post.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.blog.modules.post.domain.model.Product;

public interface ProductRepository {

    Product save(Product product);

    Optional<Product> findById(String id);

    List<Product> findByUserId(String id);

    List<Product> findAll();

    void deleteById(String id);
}
