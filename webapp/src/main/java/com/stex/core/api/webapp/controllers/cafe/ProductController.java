package com.stex.core.api.webapp.controllers.cafe;

import com.stex.core.api.cafe.models.Product;
import com.stex.core.api.cafe.services.ProductService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("api/products")
public class ProductController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public HttpEntity<List<Product>> getAllProduct() {
        List<Product> products = productService.findAllProducts();
        if (products.isEmpty()) {
            LOGGER.debug("Product's list is empty.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            products.forEach(p -> p.add(linkTo(methodOn(ProductController.class)
                    .getAllProduct()).withRel("products")));
            products.forEach(p -> p.add(linkTo(methodOn(ProductController.class)
                    .getProductById(p.getProductId())).withSelfRel()));
            LOGGER.debug("Loading all Products...\n{}", products);
            return new ResponseEntity<>(products, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public HttpEntity<Product> getProductById(@PathVariable ObjectId id) {
        Product product = productService.findByProductId(id);
        if (product == null) {
            LOGGER.debug("Cannot found Product [id: {}]", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            product.add(linkTo(methodOn(ProductController.class)
                    .getProductById(product.getProductId())).withSelfRel());
            LOGGER.debug("Successful found Product [id: {}]\n{}", id, product);
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
    }

    @PostMapping("/")
    public HttpEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdProduct.getProductId()).toUri();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(location);
        LOGGER.debug("Successful created Product:\n{}", createdProduct);
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public HttpEntity<Product> updateProduct(@PathVariable ObjectId id, @RequestBody Product product) {
        Product updateProduct = productService.findByProductId(id);
        if (updateProduct == null) {
            LOGGER.debug("Cannot found Product [id: {}]", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            if (product.getName() != null || product.getName().length() != 0) {
                updateProduct.setName(product.getName());
            }
            //TODO check if preis is null
            updateProduct.setPreis(product.getPreis());
            productService.updateProduct(updateProduct);
            updateProduct.add(linkTo(methodOn(ProductController.class)
                    .getAllProduct()).withRel("products"));
            updateProduct.add(linkTo(methodOn(ProductController.class)
                    .getProductById(updateProduct.getProductId())).withSelfRel());
            LOGGER.debug("Successful updated Product with id: [{}]\n{}", id, updateProduct);
            return new ResponseEntity<>(updateProduct, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable ObjectId id) {
        LOGGER.debug("Deleting Product:\n{}", productService.findByProductId(id));
        productService.deleteProduct(id);
        LOGGER.debug("Successful deleted Product [id: {}]", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
