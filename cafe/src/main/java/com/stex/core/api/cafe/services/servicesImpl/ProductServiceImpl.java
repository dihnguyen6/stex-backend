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

    public Product updateProduct(Product product) {
        if (productRepository.existsById(String.valueOf(product.getId()))) {
            Product updateProduct = productRepository.findById(product.getId());
            updateProduct.setName(product.getName());
            updateProduct.setPreis(product.getPreis());
            productRepository.save(updateProduct);
            LOGGER.info("Successful updated Product {}", updateProduct.toString());
            return updateProduct;
        } else {
            LOGGER.info("Cannot found Product with id: [{}]", product.getId());
            return null;
        }
    }

    public void deleteProduct(ObjectId id) {
        if (productRepository.existsById(String.valueOf(id))) {
            Product deleteProduct = productRepository.findById(id);
            productRepository.delete(deleteProduct);
            LOGGER.info("Successful deleted Product {}", deleteProduct.toString());
        } else {
            LOGGER.info("Cannot found Product with id: [{}]", id);
        }
    }

    public Product findByProductId(ObjectId id) {
        if (productRepository.existsById(String.valueOf(id))){
            Product foundProduct = productRepository.findById(id);
            LOGGER.info("Successful found Product {}", foundProduct.toString());
            return foundProduct;
        } else {
            LOGGER.info("Cannot found Product with id: [{}]", id);
            return null;
        }
    }

    public List<Product> findAllProducts() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            LOGGER.debug("Product's list ist empty.");
        }
        LOGGER.info("Loading all products ...");
        for (Product product: products) {
            LOGGER.info("\nProduct: {} \n", product);
        }
        return products;
    }
}
