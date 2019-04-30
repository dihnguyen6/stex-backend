package com.stex.core.api.webapp.controllers.cafe;

import com.stex.core.api.cafe.models.Product;
import com.stex.core.api.cafe.services.ProductService;
import com.stex.core.api.tools.ExceptionHandler.ResourceNotFoundException;
import com.stex.core.api.webapp.ResourcesAssembler.CafeResource.ProductResourceAssembler;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
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
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/products", produces = "application/hal+json")
public class ProductController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final ProductService productService;
    private final ProductResourceAssembler productResourceAssembler;

    @Autowired
    public ProductController(ProductService productService,
                             ProductResourceAssembler productResourceAssembler) {
        this.productService = productService;
        this.productResourceAssembler = productResourceAssembler;
    }

    @GetMapping("/")
    public Resources<Resource<Product>> getAllProduct() {
        List<Resource<Product>> products = productService.findAllProducts()
                .stream()
                .map(productResourceAssembler::toResource)
                .collect(Collectors.toList());
        if (products.isEmpty())
            throw new ResourceNotFoundException("Products", null, null);
        LOGGER.debug(productService.findAllProducts().toString());
        return new Resources<>(products,
                linkTo(methodOn(ProductController.class).getAllProduct()).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource<Product>> getProductById(@PathVariable ObjectId id) {
        Product product = productService.findByProductId(id);
        if (product == null)
            throw new ResourceNotFoundException("Product", "id", id);
        LOGGER.debug(product.toString());
        return ResponseEntity.created(linkTo(methodOn(ProductController.class)
                .getProductById(id)).toUri())
                .body(productResourceAssembler.toResource(product));
    }

    @GetMapping("/category/{categoryId}")
    public Resources<Resource<Product>> getAllByCategory(@PathVariable ObjectId categoryId) {
        List<Resource<Product>> products = productService.findAllProductsByCategory(categoryId)
                .stream()
                .map(productResourceAssembler::toResource)
                .collect(Collectors.toList());
        if (products.isEmpty())
            throw new ResourceNotFoundException("Products", "category", categoryId);
        LOGGER.debug(productService.findAllProductsByCategory(categoryId).toString());
        return new Resources<>(products,
                linkTo(methodOn(ProductController.class).getAllByCategory(categoryId)).withSelfRel());
    }

    @PostMapping("/")
    public ResponseEntity<Resource<Product>> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.updateProduct(product);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdProduct.getProductId()).toUri();
        LOGGER.debug("Successful created Product:{}", createdProduct);
        return ResponseEntity
                .created(location)
                .body(productResourceAssembler.toResource(createdProduct));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResourceSupport> updateProduct(@PathVariable ObjectId id,
                                                         @RequestBody Product product) {
        Product updateProduct = productService.findByProductId(id);
        if (updateProduct == null)
            throw new ResourceNotFoundException("Product", "id", id);
        if (product.getName() != null)
            updateProduct.setName(product.getName());
        updateProduct.setPreis(product.getPreis());
        productService.updateProduct(updateProduct);
        LOGGER.debug("Successful updated Product with id: [{}]{}", id, updateProduct);
        return ResponseEntity.ok(productResourceAssembler.toResource(updateProduct));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Resource<Product>> deleteProduct(@PathVariable ObjectId id) {
        Product deleteProduct = productService.findByProductId(id);
        if (deleteProduct == null)
            throw new ResourceNotFoundException("Product", "id", id);
        productService.deleteProduct(deleteProduct);
        LOGGER.debug("Successful deleted Product [id: {}]", id);
        return ResponseEntity.ok().build();
    }
}
