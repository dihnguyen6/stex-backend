package com.stex.core.api.cafe.repositories.CustomRepository;

import com.stex.core.api.cafe.models.Bill;
import com.stex.core.api.tools.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomBillRepositoryImpl implements CustomBillRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public CustomBillRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Bill> findBillByStatusAndTable(Status status, int table) {
        final Query query = new Query();
        query.addCriteria(
                Criteria.where("status").is(status).
                        andOperator(Criteria.where("table").is(table)));
        return mongoTemplate.find(query, Bill.class);
    }
}
