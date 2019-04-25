package com.stex.core.api.cafe.services.servicesImpl;

import com.stex.core.api.cafe.models.Product;
import com.stex.core.api.cafe.repositories.ProductRepository;
import com.stex.core.api.cafe.services.ProductService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(ObjectId id) {
        productRepository.delete(findByProductId(id));
    }

    public Product findByProductId(ObjectId id) {
        return productRepository.findById(id);
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }
}
