package com.stex.core.api.webapp.controllers.cafe;

import com.stex.core.api.cafe.models.Category;
import com.stex.core.api.cafe.services.CategoryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategorieController {
    private final CategoryService categorieService;

    @Autowired
    public CategorieController(CategoryService categorieService) {
        this.categorieService = categorieService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categorieService.findAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategorieById(@PathVariable ObjectId id) {
        return ResponseEntity.ok(categorieService.findCategoryById(id));
    }

    @PostMapping("/")
    public ResponseEntity<Category> createCategorie(@RequestBody Category categorie) {
        return ResponseEntity.ok(categorieService.updateCategory(categorie));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategorie(@PathVariable ObjectId id, @RequestBody Category categorie) {
        Category updateCategorie = categorieService.findCategoryById(id);
        updateCategorie.setName(categorie.getName());
        categorieService.updateCategory(updateCategorie);
        return ResponseEntity.ok(updateCategorie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategorie(@PathVariable ObjectId id) {
        Category categorie = categorieService.findCategoryById(id);
        categorieService.deleteCategory(categorie);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
