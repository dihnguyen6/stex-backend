package com.stex.core.api.cafe.services.servicesImpl;

import com.stex.core.api.cafe.models.Order;
import com.stex.core.api.cafe.repositories.OrderRepository;
import com.stex.core.api.cafe.services.OrderService;
import com.stex.core.api.tools.Status;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class OrderServiceImpl implements OrderService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(Order order) {
        order.setCreatedAt(new Date());
        order.setUpdatedAt(new Date());
        order.setStatus(Status.IN_PROGRESS);
        Order createOrder = orderRepository.save(order);
        LOGGER.info("Successful added Order {}", createOrder.toString());
        return createOrder;
    }

    /**
     * Order can only be changed when it is in the status {@link Status#IN_PROGRESS}
     *
     * @param order update Order
     */
    public Order updateOrder(Order order) {
        if (orderRepository.existsById(String.valueOf(order.getId()))) {
            Order updateOrder = orderRepository.findById(order.getId());
            updateOrder.setDescription(order.getDescription());
            updateOrder.setProduct(order.getProduct());
            updateOrder.setQuantity(order.getQuantity());
            updateOrder.setUpdatedAt(new Date());
            LOGGER.info("Successful updated Order {}", updateOrder.toString());
            orderRepository.save(updateOrder);
            return updateOrder;
        } else {
            LOGGER.info("Cannot found Order with id: [{}]", order.getId());
            return null;
        }
    }

    /**
     * Change status from {@link Status#IN_PROGRESS} to {@link Status#COMPLETED}
     *
     * @param id OrderId
     */
    public Order completeOrder(ObjectId id) {
        if (orderRepository.existsById(String.valueOf(id))) {
            Order completeOrder = orderRepository.findById(id);
            completeOrder.setStatus(Status.COMPLETED);
            completeOrder.setUpdatedAt(new Date());
            orderRepository.save(completeOrder);
            LOGGER.info("Successful completed Order {}", completeOrder.toString());
            return completeOrder;
        } else {
            LOGGER.info("Cannot found Order with id: [{}]", id);
            return null;
        }
    }

    /**
     * Change status from {@link Status#IN_PROGRESS} to {@link Status#CANCELLED}
     *
     * @param id OrderId
     */
    public Order cancelOrder(ObjectId id) {
        if (orderRepository.existsById(String.valueOf(id))) {
            Order cancelOrder = orderRepository.findById(id);
            cancelOrder.setStatus(Status.CANCELLED);
            cancelOrder.setUpdatedAt(new Date());
            orderRepository.save(cancelOrder);
            LOGGER.info("Successful cancelled Order {}", cancelOrder.toString());
            return cancelOrder;
        } else {
            LOGGER.info("Cannot found Order with id: [{}]", id);
            return null;
        }
    }

    public Order findByOrderId(ObjectId id) {
        if (orderRepository.existsById(String.valueOf(id))) {
            Order foundOrder = orderRepository.findById(id);
            LOGGER.info("Successful found Order {}", foundOrder.toString());
            return foundOrder;
        } else {
            LOGGER.info("Cannot found Order with id: [{}]", id);
            return null;
        }
    }

    public List<Order> findAllOrders() {
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            LOGGER.info("Order's list is empty");
        }
        LOGGER.info("Loading all orders ...");
        for (Order order : orders) {
            LOGGER.info("\nOrder: {} \n", order);
        }
        return orders;
    }

    public List<Order> findByOrderStatus(Status status) {
        List<Order> orders = orderRepository.findAllByStatus(status);
        if (orders.isEmpty()) {
            LOGGER.info("Order' list is empty.");
        }
        LOGGER.info("Loading all orders that are in the {} status.", status);
        for (Order order : orders) {
            LOGGER.info("\nOrder: {}\n", order);
        }
        return orderRepository.findAllByStatus(status);
    }

}
