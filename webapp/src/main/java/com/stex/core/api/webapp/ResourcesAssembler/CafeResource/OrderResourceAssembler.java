package com.stex.core.api.webapp.ResourcesAssembler.CafeResource;

import com.stex.core.api.cafe.models.Order;
import com.stex.core.api.tools.constants.Status;
import com.stex.core.api.webapp.controllers.cafe.OrderController;
import com.stex.core.api.webapp.controllers.cafe.ProductController;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class OrderResourceAssembler implements ResourceAssembler<Order, Resource<Order>> {

    @Override
    public Resource<Order> toResource(Order o) {
        Resource<Order> resource = new Resource<>(o,
                linkTo(methodOn(OrderController.class).getOrderById(o.getOrderId())).withSelfRel(),
                linkTo(methodOn(OrderController.class).getAllOrders()).withRel("orders"));
        o.getProduct().add(linkTo(methodOn(ProductController.class)
                .getProductById(o.getProduct().getProductId()))
                .withSelfRel());

        if (o.getStatus() == Status.IN_PROGRESS) {
            resource.add(linkTo(methodOn(OrderController.class)
                    .updateStatusOrder(o.getOrderId(),"complete"))
                    .withRel("complete"));
            resource.add(linkTo(methodOn(OrderController.class)
                    .updateStatusOrder(o.getOrderId(), "cancel"))
                    .withRel("cancel"));
        }
        return resource;
    }
}
