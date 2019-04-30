package com.stex.core.api.cafe.repositories;

import com.stex.core.api.cafe.models.Category;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {
    Category findByCategoryId(ObjectId id);
}
