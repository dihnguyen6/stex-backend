package com.stex.core.api.cafe.services;

import com.stex.core.api.cafe.models.Product;
import com.stex.core.api.cafe.repositories.ProductRepository;
import com.stex.core.api.tools.ExceptionHandler.ResourceNotFoundException;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProductRepository productRepository;

    public void createProduct(Product product) {
        Product newProduct = productRepository.save(product);
        LOGGER.info("Successful added Product {}", newProduct.toString());
    }

    public void updateProduct(Product product) {
        try {
            Product updateProduct = productRepository.findById(product.getId());
            updateProduct.setName(product.getName());
            updateProduct.setPreis(product.getPreis());
            productRepository.save(updateProduct);
            LOGGER.info("Successful updated Product {}", updateProduct.toString());
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Product", "id", product.getId());
        }
    }

    public void deleteProduct(Product product) {
        try {
            Product deleteProduct = productRepository.findById(product.getId());
            productRepository.delete(deleteProduct);
            LOGGER.info("Successful deleted Product {}", deleteProduct.toString());
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Product", "id", product.getId());
        }

    }

    public Product findProduct(ObjectId id) {
        try {
            Product foundProduct = productRepository.findById(id);
            LOGGER.info("Successful found Product {}", foundProduct.toString());
            return foundProduct;
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Product", "id", id);
        }
    }

    public List<Product> findAllProduct() {
        List<Product> products = productRepository.findAll();
        LOGGER.info("Loading all products ...");
        return products;
    }
}
