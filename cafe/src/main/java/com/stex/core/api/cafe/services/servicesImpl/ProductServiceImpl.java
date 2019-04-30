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

    private final ProductRepository productRepository;


    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;

    }

    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void importProduct(List<Product> products) {
        productRepository.saveAll(products);
    }

    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }

    public Product findByProductId(ObjectId id) {
        return productRepository.findById(id);
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> findAllProductsByCategory(ObjectId categoryId) {
        return productRepository.findAllByCategory(categoryId);
    }
}
