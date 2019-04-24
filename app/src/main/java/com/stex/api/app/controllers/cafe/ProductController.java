package com.stex.api.app.controllers.cafe;

import com.stex.core.api.cafe.models.Product;
import com.stex.core.api.cafe.services.ProductService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public HttpEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.findAllProducts();
        if (products.isEmpty()) {
            return new ResponseEntity<List<Product>>(HttpStatus.NO_CONTENT);
        } else {
            products.forEach(p -> p.add(linkTo(methodOn(ProductController.class)
                    .getAllProducts()).withRel("products")));
            products.forEach(p -> p.add(linkTo(methodOn(ProductController.class)
                    .getProductById(p.getProductId())).withSelfRel()));

            return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public HttpEntity<Product> getProductById(@PathVariable ObjectId id) {
        Product product = productService.findByProductId(id);
        if (product == null) {
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        } else {
            product.add(linkTo(methodOn(ProductController.class).getProductById(product.getProductId())).withSelfRel());
            return new ResponseEntity<Product>(product, HttpStatus.OK);
        }
    }

    @PostMapping("/")
    public HttpEntity<Product> createProduct(@RequestBody Product product) {
        Product createProduct = productService.createProduct(product);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/products/{id}")
                .buildAndExpand(createProduct.getProductId()).toUri();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(location);
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }
}
