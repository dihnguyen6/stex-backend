package com.stex.core.api.cafe.services.servicesImpl;

import com.stex.core.api.cafe.models.Category;
import com.stex.core.api.cafe.repositories.CategoryRepository;
import com.stex.core.api.cafe.services.CategoryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category updateCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void importCategories(List<Category> categories) {
        categoryRepository.saveAll(categories);
    }

    @Override
    public void deleteCategory(Category category) {
        categoryRepository.delete(category);
    }

    @Override
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findCategoryById(ObjectId id) {
        return categoryRepository.findByCategoryId(id);
    }
}
