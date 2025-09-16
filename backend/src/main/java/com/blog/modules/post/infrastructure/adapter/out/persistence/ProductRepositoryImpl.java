package com.blog.modules.post.infrastructure.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.blog.modules.post.domain.model.Product;
import com.blog.modules.post.domain.port.out.ProductRepository;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final MongoTemplate mongoTemplate;

    public ProductRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Product save(Product product) {
        return mongoTemplate.save(product);
    }

    @Override
    public Optional<Product> findById(String id) {
        Product product = mongoTemplate.findById(id, Product.class);
        return Optional.ofNullable(product);
    }

    @Override
    public List<Product> findByUserId(String userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        return mongoTemplate.find(query, Product.class);
    }

    @Override
    public List<Product> findAll() {
        return mongoTemplate.findAll(Product.class);
    }

    @Override
    public void deleteById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        mongoTemplate.remove(query, Product.class);
    }
}
