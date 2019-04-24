package com.stex.core.api.cafe.repositories;

import com.stex.core.api.cafe.models.Product;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ProductRepository extends MongoRepository<Product, String> {
    Product findById (ObjectId id);
}
