package com.stex.core.api.cafe.services.servicesImpl;

import com.stex.core.api.cafe.models.Product;
import com.stex.core.api.cafe.repositories.ProductRepository;
import com.stex.core.api.cafe.services.ProductService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(Product product) {
        Product newProduct = productRepository.save(product);
        LOGGER.info("Successful added Product {}", newProduct.toString());
        return newProduct;
    }

    public void updateProduct(Product product) {
        Product updateProduct = productRepository.findById(product.getProductId());
        updateProduct.setName(product.getName());
        updateProduct.setPreis(product.getPreis());
        productRepository.save(updateProduct);
        LOGGER.info("Successful updated Product {}", updateProduct.toString());
    }

    public void deleteProduct(ObjectId id) {
        Product deleteProduct = productRepository.findById(id);
        productRepository.delete(deleteProduct);
        LOGGER.info("Successful deleted Product {}", deleteProduct.toString());
    }

    public Product findByProductId(ObjectId id) {
        Product foundProduct = productRepository.findById(id);
        LOGGER.info("Successful found Product {}", foundProduct.toString());
        return foundProduct;
    }

    public List<Product> findAllProducts() {
        List<Product> products = productRepository.findAll();
        LOGGER.info("Loading all products ...");
        return products;
    }
}
