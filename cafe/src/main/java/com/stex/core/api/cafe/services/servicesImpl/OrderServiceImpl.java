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
    public void updateOrder(Order order) {
        Order updateOrder = orderRepository.findById(order.getOrderId());
        updateOrder.setDescription(order.getDescription());
        updateOrder.setProduct(order.getProduct());
        updateOrder.setQuantity(order.getQuantity());
        updateOrder.setUpdatedAt(new Date());
        LOGGER.info("Successful updated Order {}", updateOrder.toString());
        orderRepository.save(updateOrder);
    }

    /**
     * Change status from {@link Status#IN_PROGRESS} to {@link Status#COMPLETED}
     *
     * @param id OrderId
     */
    public void completeOrder(ObjectId id) {
        Order completeOrder = orderRepository.findById(id);
        completeOrder.setStatus(Status.COMPLETED);
        completeOrder.setUpdatedAt(new Date());
        orderRepository.save(completeOrder);
        LOGGER.info("Successful completed Order {}", completeOrder.toString());
    }

    /**
     * Change status from {@link Status#IN_PROGRESS} to {@link Status#CANCELLED}
     *
     * @param id OrderId
     */
    public void cancelOrder(ObjectId id) {
        Order cancelOrder = orderRepository.findById(id);
        cancelOrder.setStatus(Status.CANCELLED);
        cancelOrder.setUpdatedAt(new Date());
        orderRepository.save(cancelOrder);
        LOGGER.info("Successful cancelled Order {}", cancelOrder.toString());

    }

    public Order findByOrderId(ObjectId id) {
        Order foundOrder = orderRepository.findById(id);
        LOGGER.info("Successful found Order {}", foundOrder.toString());
        return foundOrder;
    }

    public List<Order> findAllOrders() {
        LOGGER.info("Loading all orders ...");
        return orderRepository.findAll();
    }

    public List<Order> findByOrderStatus(Status status) {
        LOGGER.info("Loading all orders that are in the {} status.", status);
        return orderRepository.findAllByStatus(status);
    }

}
