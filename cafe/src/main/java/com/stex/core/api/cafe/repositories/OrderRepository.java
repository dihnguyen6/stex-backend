package com.stex.core.api.cafe.repositories;

import com.stex.core.api.cafe.models.Order;
import com.stex.core.api.tools.Status;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
    Order findById (ObjectId id);
    List<Order> findAllByStatus(Status status);
}
