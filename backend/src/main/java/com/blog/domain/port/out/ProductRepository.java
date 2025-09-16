package com.blog.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.blog.domain.model.Product;

public interface ProductRepository {

    Product save(Product product);

    Optional<Product> findById(String id);

    List<Product> findByUserId(String id);

    List<Product> findAll();

    void deleteById(String id);
}
