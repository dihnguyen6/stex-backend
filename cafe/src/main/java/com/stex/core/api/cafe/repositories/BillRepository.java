package com.stex.core.api.cafe.repositories;

import com.stex.core.api.cafe.models.Bill;
import com.stex.core.api.tools.Status;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BillRepository extends MongoRepository<Bill, String> {
    Bill findById (ObjectId id);
    List<Bill> findAllByStatus(Status status);
    Bill findByStatusAndTable (Status status, int table);
}
