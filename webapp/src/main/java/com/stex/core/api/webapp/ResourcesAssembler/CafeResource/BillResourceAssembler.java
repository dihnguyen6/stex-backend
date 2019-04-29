package com.stex.core.api.webapp.ResourcesAssembler.CafeResource;

import com.stex.core.api.cafe.models.Bill;
import com.stex.core.api.tools.constants.Status;
import com.stex.core.api.webapp.controllers.cafe.BillController;
import com.stex.core.api.webapp.controllers.cafe.OrderController;
import com.stex.core.api.webapp.controllers.cafe.ProductController;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class BillResourceAssembler implements ResourceAssembler<Bill, Resource<Bill>> {

    @Override
    public Resource<Bill> toResource(Bill b) {
        Resource<Bill> resource = new Resource<>(b,
                linkTo(methodOn(BillController.class)
                        .getBillById(b.getBillId()))
                        .withSelfRel(),
                linkTo(methodOn(BillController.class)
                        .getAllBills())
                        .withRel("bills"));

        b.getOrders().forEach(o -> o.getProduct().add(
                linkTo(methodOn(ProductController.class)
                        .getProductById(o.getProduct()
                                .getProductId()))
                        .withSelfRel()));

        b.getOrders().forEach(o -> o.add(linkTo(methodOn(OrderController.class)
                .getOrderById(o.getOrderId()))
                .withSelfRel()));

        if (b.getStatus() == Status.IN_PROGRESS) {
            resource.add(linkTo(methodOn(BillController.class)
                    .updateStatusBill(b.getBillId(), "complete"))
                    .withRel("checkout"));

            resource.add(linkTo(methodOn(BillController.class)
                    .updateStatusBill(b.getBillId(), "cancel"))
                    .withRel("cancel"));
        }
        return resource;
    }
}
