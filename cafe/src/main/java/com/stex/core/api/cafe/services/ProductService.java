package com.stex.core.api.cafe.services;

import com.stex.core.api.cafe.models.Product;
import org.bson.types.ObjectId;

import java.util.List;

public interface ProductService {
    Product findByProductId(ObjectId id);
    List<Product> findAllProducts();

    Product createProduct(Product product);
    void deleteProduct(ObjectId id);
    void updateProduct(Product product);


}
