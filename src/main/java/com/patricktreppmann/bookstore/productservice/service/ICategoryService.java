package com.patricktreppmann.bookstore.productservice.service;

import com.patricktreppmann.bookstore.productservice.entity.Category;
import com.patricktreppmann.bookstore.productservice.entity.CategoryRequest;
import com.patricktreppmann.bookstore.productservice.error.CategoryNotFoundException;

import java.util.List;

public interface ICategoryService {
    Category createCategory(CategoryRequest category);
    List<Category> fetchCategories();
    Category fetchCategoryById(Long categoryId) throws CategoryNotFoundException;
    void deleteCategory(Long categoryId) throws CategoryNotFoundException;
}
