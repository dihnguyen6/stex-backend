package com.stex.core.api.cafe.repositories;

import com.stex.core.api.cafe.models.Product;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    Product findById (ObjectId id);
    @Query("{'category.categoryId': ?0}")
    List<Product> findAllByCategory(ObjectId categoryId);
}
