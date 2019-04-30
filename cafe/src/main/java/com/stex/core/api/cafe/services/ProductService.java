package com.stex.core.api.cafe.services;

import com.stex.core.api.cafe.models.Product;
import org.bson.types.ObjectId;

import java.util.List;

public interface ProductService {
    Product findByProductId(ObjectId id);
    List<Product> findAllProducts();
    List<Product> findAllProductsByCategory(ObjectId categoryId);

    void deleteProduct(Product product);
    Product updateProduct(Product product);

    void importProduct(List<Product> products);
}
