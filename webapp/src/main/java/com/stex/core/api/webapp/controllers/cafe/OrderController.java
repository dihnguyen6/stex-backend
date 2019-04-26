package com.stex.core.api.webapp.controllers.cafe;

import com.stex.core.api.cafe.models.Bill;
import com.stex.core.api.cafe.models.Order;
import com.stex.core.api.cafe.services.BillService;
import com.stex.core.api.cafe.services.OrderService;
import com.stex.core.api.tools.Status;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final OrderService orderService;

    private final BillService biilService;

    public OrderController(OrderService orderService, BillService biilService) {
        this.orderService = orderService;
        this.biilService = biilService;
    }

    @GetMapping("/")
    public HttpEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.findAllOrders();
        if (orders.isEmpty()) {
            LOGGER.debug("Order's list is empty.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            addHateoasToListOrders(orders);
            LOGGER.debug("Loading all Orders... {}", orders);
            return new ResponseEntity<>(orders, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public HttpEntity<Order> getOrderById(@PathVariable ObjectId id) {
        Order order = orderService.findByOrderId(id);
        if (order == null) {
            LOGGER.debug("Cannot found Order [id: {}]", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            addHateoasToOrder(order);
            LOGGER.debug("Successful found Order [id: {}]{}", id, order);
            return new ResponseEntity<>(order, HttpStatus.OK);
        }
    }

    @GetMapping("/status={status}")
    public HttpEntity<List<Order>> getAllOrdersByStatus(@PathVariable Status status) {
        List<Order> orders = orderService.findByOrderStatus(status);
        if (orders == null) {
            LOGGER.debug("Order' list that are in the {} status is empty.", status);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            addHateoasToListOrders(orders);
            LOGGER.debug("Loading all Orders that are in [{}} status.{}", status, orders);
            return new ResponseEntity<>(orders, HttpStatus.OK);
        }
    }

    @PostMapping("/")
    public HttpEntity<Order> createOrder(@RequestBody Order order) {
        order.setStatus(Status.IN_PROGRESS);
        order.setCreatedAt(new Date());
        order.setUpdatedAt(new Date());
        Order createdOrder = orderService.createOrder(order);

        Bill bill = biilService.findByBillId(order.getBill().getBillId());

        if (bill.getOrders() == null) {
            bill.setOrders(new ArrayList<>());
            bill.getOrders().add(order);
        } else {
            bill.getOrders().add(order);
        }
        biilService.updateBill(bill);
        LOGGER.debug("Successful created Order:{}", orderService.findByOrderId(createdOrder.getOrderId()));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdOrder.getOrderId()).toUri();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(location);
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public HttpEntity<Order> updateOrder(@PathVariable ObjectId id, @RequestBody Order order) {
        Order updateOrder = orderService.findByOrderId(id);
        if (updateOrder == null) {
            LOGGER.debug("Cannot found Order [id: {}]", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            order.setUpdatedAt(new Date());
            if (order.getDescription() != null) {
                updateOrder.setDescription(order.getDescription());
            }
            if (order.getProduct() != null) {
                updateOrder.setProduct(order.getProduct());
            }
            //TODO check if quantity is null
            updateOrder.setQuantity(order.getQuantity());
            orderService.updateOrder(order);
            addHateoasToOrder(updateOrder);
            LOGGER.debug("Successful updated Order with id: [{}]{}", id, updateOrder);
            return new ResponseEntity<>(updateOrder, HttpStatus.OK);
        }
    }

    @PutMapping("/{id}?completed")
    public HttpEntity<Order> completeOrder(@PathVariable ObjectId id) {
        Order completeOrder = orderService.findByOrderId(id);
        if (completeOrder == null) {
            LOGGER.debug("Cannot found Order [id: {}]", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            if (completeOrder.getStatus() != Status.IN_PROGRESS) {
                LOGGER.debug("Action is not allowed." +
                        "Cannot change Order that is in {} status.", completeOrder.getStatus());
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            } else {
                completeOrder.setUpdatedAt(new Date());
                completeOrder.setStatus(Status.COMPLETED);
                orderService.updateOrder(completeOrder);
                addHateoasToOrder(completeOrder);
                LOGGER.debug("Successful completed Order with [id: {}]{}", id, completeOrder);
                return new ResponseEntity<>(completeOrder, HttpStatus.OK);
            }
        }
    }

    @PutMapping("/{id}?cancelled")
    public HttpEntity<Order> cancelOrder(@PathVariable ObjectId id) {
        Order cancelOrder = orderService.findByOrderId(id);
        if (cancelOrder == null) {
            LOGGER.debug("Cannot found Order [id: {}]", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            if (cancelOrder.getStatus() != Status.IN_PROGRESS) {
                LOGGER.debug("Action is not allowed." +
                        "Cannot change Order that is in {} status.", cancelOrder.getStatus());
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            } else {
                cancelOrder.setUpdatedAt(new Date());
                cancelOrder.setStatus(Status.CANCELLED);
                orderService.updateOrder(cancelOrder);
                addHateoasToOrder(cancelOrder);
                LOGGER.debug("Successful cancelled Order with [id: {}]{}", id, cancelOrder);
                return new ResponseEntity<>(cancelOrder, HttpStatus.OK);
            }
        }
    }

    private void addHateoasToListOrders(List<Order> orders) {
        orders.forEach(o -> o.add(linkTo(methodOn(OrderController.class)
                .getAllOrders())
                .withRel("orders")));
        orders.forEach(o -> o.add(linkTo(methodOn(OrderController.class)
                .getOrderById(o.getOrderId()))
                .withSelfRel()));
        //TODO add Link to embedded Product
        orders.forEach(o -> o.getProduct()
                .add(linkTo(methodOn(ProductController.class)
                        .getProductById(o.getProduct().getProductId()))
                        .withSelfRel()));
        orders.forEach(o -> o.getBill()
                .add(linkTo(methodOn(BillController.class)
                        .getBillById(o.getBill().getBillId()))
                        .withSelfRel()));
    }

    private void addHateoasToOrder(Order order) {
        order.add(linkTo(methodOn(OrderController.class)
                .getAllOrders())
                .withRel("orders"));
        order.add(linkTo(methodOn(OrderController.class)
                .getOrderById(order.getOrderId()))
                .withSelfRel());
        order.getProduct().add(linkTo(methodOn(ProductController.class)
                .getProductById(order.getProduct().getProductId()))
                .withSelfRel());
        order.getBill().add(linkTo(methodOn(BillController.class)
                .getBillById(order.getBill().getBillId()))
                .withSelfRel());
    }
}
