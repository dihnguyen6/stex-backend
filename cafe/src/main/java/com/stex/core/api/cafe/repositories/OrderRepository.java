package com.stex.core.api.cafe.repositories;

import com.stex.core.api.cafe.models.Order;
import com.stex.core.api.tools.constants.Status;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    Order findById (ObjectId id);
    @Query("{'status': ?0}")
    List<Order> findAllByStatus(Status status);
    //List<Order> findAllByBill_Id (ObjectId id);
}
