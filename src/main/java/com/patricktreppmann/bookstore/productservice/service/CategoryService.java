package com.patricktreppmann.bookstore.productservice.service;

import com.patricktreppmann.bookstore.productservice.entity.Category;
import com.patricktreppmann.bookstore.productservice.entity.CategoryRequest;
import com.patricktreppmann.bookstore.productservice.error.CategoryNotFoundException;
import com.patricktreppmann.bookstore.productservice.repository.CategoryRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService{
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category createCategory(CategoryRequest categoryRequest) throws DataIntegrityViolationException {
        return categoryRepository.save(new Category(categoryRequest.getName()));
    }

    @Override
    public List<Category> fetchCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category fetchCategoryById(Long categoryId) throws CategoryNotFoundException {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException("category with id " + categoryId + " not found"));
    }

    @Override
    public void deleteCategory(Long categoryId) throws CategoryNotFoundException {
        Category category = fetchCategoryById(categoryId);
        categoryRepository.delete(category);
    }
}
