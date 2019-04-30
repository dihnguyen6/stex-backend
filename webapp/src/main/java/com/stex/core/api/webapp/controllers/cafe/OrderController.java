package com.stex.core.api.webapp.controllers.cafe;

import com.stex.core.api.cafe.models.Order;
import com.stex.core.api.cafe.services.OrderService;
import com.stex.core.api.tools.ExceptionHandler.ResourceForbiddenException;
import com.stex.core.api.tools.ExceptionHandler.ResourceNotFoundException;
import com.stex.core.api.tools.constants.Status;
import com.stex.core.api.webapp.ResourcesAssembler.CafeResource.OrderResourceAssembler;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/api/orders", produces = "application/hal+json")
public class OrderController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final OrderService orderService;
    private final OrderResourceAssembler orderResourceAssembler;

    @Autowired
    public OrderController(OrderService orderService,
                           OrderResourceAssembler orderResourceAssembler) {
        this.orderService = orderService;
        this.orderResourceAssembler = orderResourceAssembler;
    }

    @GetMapping("/")
    public Resources<Resource<Order>> getAllOrders() {
        List<Resource<Order>> orders = orderService.findAllOrders()
                .stream()
                .map(orderResourceAssembler::toResource)
                .collect(Collectors.toList());
        if (orders.isEmpty())
            throw new ResourceNotFoundException("Orders", null, null);
        LOGGER.debug(orderService.findAllOrders().toString());
        return new Resources<>(orders,
                linkTo(methodOn(OrderController.class).getAllOrders()).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource<Order>> getOrderById(@PathVariable ObjectId id) {
        Order order = orderService.findByOrderId(id);
        if (order == null)
            throw new ResourceNotFoundException("Order", "id", id);
        LOGGER.debug(order.toString());
        return ResponseEntity.created(linkTo(methodOn(OrderController.class)
                .getOrderById(id)).toUri())
                .body(orderResourceAssembler.toResource(order));
    }

    @GetMapping("/progress")
    public Resources<Resource<Order>> getProgressOrders() {
        List<Resource<Order>> progressOrders = orderService.findByOrderStatus(Status.IN_PROGRESS)
                .stream()
                .map(orderResourceAssembler::toResource)
                .collect(Collectors.toList());
        if (progressOrders.isEmpty())
            throw new ResourceNotFoundException("Orders", "status", Status.IN_PROGRESS);
        LOGGER.debug(progressOrders.toString());
        return new Resources<>(progressOrders,
                linkTo(methodOn(OrderController.class).getProgressOrders()).withSelfRel());
    }

    @GetMapping("/complete")
    public Resources<Resource<Order>> getCompleteOrders() {
        List<Resource<Order>> completeOrders = orderService.findByOrderStatus(Status.COMPLETED)
                .stream()
                .map(orderResourceAssembler::toResource)
                .collect(Collectors.toList());
        if (completeOrders.isEmpty())
            throw new ResourceNotFoundException("Orders", "status", Status.COMPLETED);
        LOGGER.debug(completeOrders.toString());
        return new Resources<>(completeOrders,
                linkTo(methodOn(OrderController.class).getProgressOrders()).withSelfRel());
    }

    @GetMapping("/cancel")
    public Resources<Resource<Order>> getCancelOrders() {
        List<Resource<Order>> cancelOrders = orderService.findByOrderStatus(Status.COMPLETED)
                .stream()
                .map(orderResourceAssembler::toResource)
                .collect(Collectors.toList());
        if (cancelOrders.isEmpty())
            throw new ResourceNotFoundException("Orders", "status", Status.CANCELLED);
        LOGGER.debug(cancelOrders.toString());
        return new Resources<>(cancelOrders,
                linkTo(methodOn(OrderController.class).getProgressOrders()).withSelfRel());
    }

    @PostMapping("/")
    public ResponseEntity<Resource<Order>> createOrder(@RequestBody Order order) {
        order.setStatus(Status.IN_PROGRESS);
        order.setCreatedAt(new Date());
        order.setUpdatedAt(new Date());
        Order createdOrder = orderService.updateOrder(order);
        LOGGER.debug("Successful created Order:{}", createdOrder);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdOrder.getOrderId()).toUri();
        return ResponseEntity.created(location)
                .body(orderResourceAssembler.toResource(createdOrder));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResourceSupport> updateOrder(@PathVariable ObjectId id,
                                                       @RequestBody Order order) {
        Order updateOrder = orderService.findByOrderId(id);
        if (updateOrder == null)
            throw new ResourceNotFoundException("Order", "id", id);
        order.setUpdatedAt(new Date());
        if (order.getDescription() != null) {
            updateOrder.setDescription(order.getDescription());
        }
        if (order.getProduct() != null) {
            updateOrder.setProduct(order.getProduct());
        }
        updateOrder.setQuantity(order.getQuantity());
        orderService.updateOrder(order);
        LOGGER.debug("Successful updated Order with id: [{}]{}", id, updateOrder);
        return ResponseEntity.ok(orderResourceAssembler.toResource(updateOrder));

    }

    @PutMapping("/{id}/{status}")
    public ResponseEntity<ResourceSupport> updateStatusOrder(@PathVariable ObjectId id,
                                                             @PathVariable String status) {
        Order order = orderService.findByOrderId(id);
        if (order == null)
            throw new ResourceNotFoundException("Order", "id", id);
        if (order.getStatus() != Status.IN_PROGRESS)
            throw new ResourceForbiddenException("Order", "status", order.getStatus());
        order.setUpdatedAt(new Date());
        if (status.equals("complete")) {
            order.setStatus(Status.COMPLETED);
        } else if (status.equals("cancel")) {
            order.setStatus(Status.CANCELLED);
        }
        orderService.updateOrder(order);
        LOGGER.debug("Successful {} Order with [id: {}]{}", status, id, order);
        return ResponseEntity.ok(orderResourceAssembler.toResource(order));
    }
}
