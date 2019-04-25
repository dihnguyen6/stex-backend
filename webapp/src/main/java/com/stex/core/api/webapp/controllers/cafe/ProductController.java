package com.stex.core.api.webapp.controllers.cafe;

import com.stex.core.api.cafe.models.Product;
import com.stex.core.api.cafe.services.ProductService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public HttpEntity<List<Product>> getAllProduct() {
        List<Product> products = productService.findAllProducts();
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            products.forEach(p -> p.add(linkTo(methodOn(ProductController.class)
                    .getAllProduct()).withRel("products")));
            products.forEach(p -> p.add(linkTo(methodOn(ProductController.class)
            .getProductById(p.getProductId())).withSelfRel()));
            return new ResponseEntity<>(products, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public HttpEntity<Product> getProductById(@PathVariable ObjectId id) {
        Product product = productService.findByProductId(id);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            product.add(linkTo(methodOn(ProductController.class)
                    .getProductById(product.getProductId())).withSelfRel());
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
    }

    @PostMapping("/")
    public HttpEntity<Product> createProduct(@RequestBody Product product) {
        productService.createProduct(product);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(product.getProductId()).toUri();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(location);
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public HttpEntity<Product> updateProduct(@PathVariable ObjectId id, @RequestBody Product product) {
        Product updateProduct = productService.findByProductId(id);
        if (updateProduct == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            productService.updateProduct(product);
            return new ResponseEntity<>(updateProduct, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable ObjectId id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
