package com.stex.core.api.cafe.repositories;

import com.stex.core.api.cafe.models.Bill;
import com.stex.core.api.tools.Status;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends MongoRepository<Bill, String> {
    Bill findById (ObjectId id);
    List<Bill> findAllByStatus(Status status);
    Bill findByStatusAndTable (Status status, int table);
}
