package com.stex.core.api.cafe.repositories;

import com.stex.core.api.cafe.models.Order;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
    Order findById (ObjectId id);
}
