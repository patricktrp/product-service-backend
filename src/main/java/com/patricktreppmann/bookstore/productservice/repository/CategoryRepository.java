package com.patricktreppmann.bookstore.productservice.repository;

import com.patricktreppmann.bookstore.productservice.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByNameIgnoreCase(String name);
}
