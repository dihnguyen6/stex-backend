package com.stex.core.api.webapp.ResourcesAssembler.CafeResource;

import com.stex.core.api.cafe.models.Product;
import com.stex.core.api.webapp.controllers.cafe.ProductController;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class ProductResourceAssembler implements ResourceAssembler<Product, Resource<Product>> {

    @Override
    public Resource<Product> toResource(Product p) {
        return new Resource<>(p,
                linkTo(methodOn(ProductController.class).getProductById(p.getProductId())).withSelfRel(),
                linkTo(methodOn(ProductController.class).getAllProduct()).withRel("products"));
    }
}
