package com.patricktreppmann.bookstore.productservice.repository;

import com.patricktreppmann.bookstore.productservice.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository underTest;

    @Test
    void itShouldFindCategoryByName() {
        // given
        String categoryName = "Computing";
        Category category = new Category(categoryName);
        underTest.save(category);

        // when
        Category foundCategory = underTest.findByNameIgnoreCase(categoryName);

        // then
        assertThat(foundCategory.equals(category)).isTrue();
    }

    @Test
    void itShouldFindCategoryByNameIgnoreCase() {
        // given
        String categoryName = "Computing";
        Category category = new Category(categoryName);
        underTest.save(category);

        // when
        Category foundCategory = underTest.findByNameIgnoreCase("cOmPuTinG");

        // then
        assertThat(foundCategory.equals(category)).isTrue();
    }

    @Test
    void itShouldReturnNullForNonExistentCategory() {
        // given
        String categoryName = "Computing";
        Category category = new Category(categoryName);
        underTest.save(category);

        // when
        Category foundCategory = underTest.findByNameIgnoreCase("Computer");

        // then
        assertThat(foundCategory).isNull();
    }
}