package com.stex.core.api.cafe.repositories;

import com.stex.core.api.cafe.models.Bill;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends MongoRepository<Bill, String> {
    Bill findById (ObjectId id);
}
