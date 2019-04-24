package com.stex.core.api.cafe.services;

import com.stex.core.api.cafe.models.Order;
import com.stex.core.api.tools.Status;
import org.bson.types.ObjectId;

import java.util.List;

public interface OrderService {
    Order findByOrderId(ObjectId id);
    List<Order> findAllOrders();
    List<Order> findByOrderStatus(Status status);

    Order createOrder(Order order);
    Order updateOrder(Order order);
    Order completeOrder(ObjectId id);
    Order cancelOrder(ObjectId id);
}
