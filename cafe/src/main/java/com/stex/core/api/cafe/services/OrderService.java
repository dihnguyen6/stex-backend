package com.stex.core.api.cafe.services;

import com.stex.core.api.cafe.models.Order;
import com.stex.core.api.cafe.repositories.OrderRepository;
import com.stex.core.api.tools.ExceptionHandler.ResourceNotFoundException;
import com.stex.core.api.tools.Status;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class OrderService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderRepository orderRepository;

    public void createOrder(Order order) {
        order.setCreatedAt(new Date());
        order.setUpdatedAt(new Date());
        orderRepository.save(order);
        LOGGER.info("Successful added Order {}", order.toString());
    }

    /**
     * Order can only be changed when it is in the status {@link Status#IN_PROGRESS}
     *
     * @param order update Order
     * @return HTTP.Status
     */
    public HttpStatus updateOrder(Order order) {
        try {
            Order updateOrder = orderRepository.findById(order.getId());
            if (updateOrder.getStatus() != Status.IN_PROGRESS) {
                LOGGER.debug("Method not allowed. " +
                        "You cannot change an order that is in the {} status.", updateOrder.getStatus());
                return HttpStatus.METHOD_NOT_ALLOWED;
            }
            updateOrder.setDescription(order.getDescription());
            updateOrder.setProduct(order.getProduct());
            updateOrder.setQuantity(order.getQuantity());
            updateOrder.setUpdatedAt(new Date());
            LOGGER.info("Successful updated Order {}", updateOrder.toString());
            orderRepository.save(updateOrder);
            return HttpStatus.ACCEPTED;
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Order", "id", order.getId());
        }
    }

    /**
     * Change status from {@link Status#IN_PROGRESS} to {@link Status#COMPLETED}
     *
     * @param order complete Order
     * @return HTTP.Status
     */
    public HttpStatus completeOrder(Order order) {
        try {
            Order completeOrder = orderRepository.findById(order.getId());
            if (completeOrder.getStatus() != Status.IN_PROGRESS) {
                LOGGER.debug("Method not allowed." +
                        "You cannot complete order that is in the {} status.", completeOrder.getStatus());
                return HttpStatus.METHOD_NOT_ALLOWED;
            }
            completeOrder.setStatus(Status.COMPLETED);
            completeOrder.setUpdatedAt(new Date());
            orderRepository.save(completeOrder);
            LOGGER.info("Successful completed Order {}", completeOrder.toString());
            return HttpStatus.ACCEPTED;
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Order", "id", order.getId());
        }
    }

    /**
     * Change status from {@link Status#IN_PROGRESS} to {@link Status#CANCELLED}
     *
     * @param order cancel Order
     * @return HTTP.Status
     */
    public HttpStatus cancelOrder(Order order) {
        try {
            Order cancelOrder = orderRepository.findById(order.getId());
            if (cancelOrder.getStatus() != Status.IN_PROGRESS) {
                LOGGER.debug("Method not allowed." +
                        "You cannot cancel Order that is in the {} status.", cancelOrder.getStatus());
                return HttpStatus.METHOD_NOT_ALLOWED;
            }
            cancelOrder.setStatus(Status.CANCELLED);
            cancelOrder.setUpdatedAt(new Date());
            orderRepository.save(cancelOrder);
            LOGGER.info("Successful cancelled Order {}", cancelOrder.toString());
            return HttpStatus.ACCEPTED;
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Order", "id", order.getId());
        }
    }

    public Order getOrder(ObjectId id) {
        try {
            Order foundOrder = orderRepository.findById(id);
            LOGGER.info("Successful found Order {}", foundOrder.toString());
            return foundOrder;
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Order", "id", id);
        }
    }

    public List<Order> getAllOrders() {
        LOGGER.info("Loading all orders ...");
        return orderRepository.findAll();
    }
}
