package com.patricktreppmann.bookstore.productservice.controller;

import com.patricktreppmann.bookstore.productservice.entity.Category;
import com.patricktreppmann.bookstore.productservice.entity.CategoryRequest;
import com.patricktreppmann.bookstore.productservice.error.CategoryNotFoundException;
import com.patricktreppmann.bookstore.productservice.service.ICategoryService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final ICategoryService categoryService;

    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public Category createCategory(@RequestBody @Valid CategoryRequest categoryRequest) throws DataIntegrityViolationException {
        return categoryService.createCategory(categoryRequest);
    }

    @GetMapping
    public List<Category> fetchCategories() {
        return categoryService.fetchCategories();
    }

    @GetMapping("/{categoryId}")
    public Category fetchCategoryById(@PathVariable Long categoryId) throws CategoryNotFoundException {
        return categoryService.fetchCategoryById(categoryId);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) throws CategoryNotFoundException {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok().body("category with id " + categoryId + " successfully deleted");
    }
}