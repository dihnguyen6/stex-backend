package com.stex.core.api.cafe.services;

import com.stex.core.api.cafe.models.Product;
import org.bson.types.ObjectId;

import java.util.List;

public interface ProductService {
    Product findByProductId(ObjectId id);
    List<Product> findAllProducts();

    void deleteProduct(Product product);
    Product updateProduct(Product product);
}
