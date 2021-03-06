package com.stex.core.api.cafe.services.servicesImpl;

import com.stex.core.api.cafe.models.Order;
import com.stex.core.api.cafe.repositories.OrderRepository;
import com.stex.core.api.cafe.services.OrderService;
import com.stex.core.api.tools.constants.Status;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order updateOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order findByOrderId(ObjectId id) {
        return orderRepository.findById(id);
    }

    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> findByOrderStatus(Status status) {
        return orderRepository.findAllByStatus(status);
    }

}
