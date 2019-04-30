package com.stex.core.api.cafe.services;

import com.stex.core.api.cafe.models.Category;
import org.bson.types.ObjectId;

import java.util.List;

public interface CategoryService {
    Category updateCategory(Category category);
    void importCategories(List<Category> categories);
    void deleteCategory(Category category);

    List<Category> findAllCategories();
    Category findCategoryById(ObjectId id);
}
