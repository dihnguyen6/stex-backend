package com.stex.api.app.controllers.cafe;

import com.stex.core.api.cafe.models.Product;
import com.stex.core.api.cafe.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@Component
public class ProductController {

    @Autowired
    private ProductService productService;

   /* @GetMapping("/")
    public HttpEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.findAllProducts();
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            products.forEach(p -> p.add(linkTo(methodOn(ProductController.class)
                    .getAllProducts()).withRel("products")));
            products.forEach(p -> p.add(linkTo(methodOn(ProductController.class)
                    .getProductById(p.getId())).withSelfRel()));

            return new ResponseEntity<>(products, HttpStatus.OK);
        }
    }*/

    /*@GetMapping("/{id}")
    public HttpEntity<Product> getProductById(@PathVariable ObjectId id) {
        Product product = productService.findByProductId(id);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            product.add(linkTo(methodOn(ProductController.class).getProductById(product.getId())).withSelfRel());
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
    }

    @PostMapping("/")
    public HttpEntity<Product> createProduct(@RequestBody Product product) {
        Product createProduct = productService.createProduct(product);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/products/{id}")
                .buildAndExpand(createProduct.getId()).toUri();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(location);
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }*/

    @PostMapping("/")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createProduct = productService.createProduct(product);
        return new ResponseEntity<Product>(createProduct, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public String get() {
        return "Hallo User";
    }
}
