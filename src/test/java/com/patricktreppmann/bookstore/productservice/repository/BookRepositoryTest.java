package com.patricktreppmann.bookstore.productservice.repository;

import com.patricktreppmann.bookstore.productservice.entity.Book;
import com.patricktreppmann.bookstore.productservice.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.Locale;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class BookRepositoryTest {
    @Autowired
    private BookRepository underTest;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void itShouldFindAllBooksByMatchingAuthor() {
        // given
        Category category = new Category("Fantasy");
        categoryRepository.save(category);

        Book book = Book.builder()
                .author("J.K. Rowling")
                .category(category)
                .coverImgUrl("http://link-to-image/harry-potter")
                .isbn("1234-1234-1234-1234")
                .name("Harry Potter and the Philosopher's Stone")
                .price(10.99)
                .build();
        underTest.save(book);

        // when
        String searchQuery = "J.K. Rowling";
        String searchText = "%" + searchQuery.toLowerCase(Locale.ROOT) + "%";
        Specification<Book> spec = (root, query, criteriaBuilder) -> {
            Predicate authorPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("author")), searchText);
            Predicate namePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), searchText);
            Predicate isbnPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("isbn")), searchText);
            return criteriaBuilder.or(isbnPredicate, namePredicate, authorPredicate);
        };

        Page<Book> bookPage = underTest.findAll(spec, PageRequest.of(0, 10));

        // then
        assertThat(bookPage.getTotalElements()).isEqualTo(1);
        assertThat(bookPage.getTotalPages()).isEqualTo(1);
        assertThat(bookPage.getContent().get(0)).isEqualTo(book);
    }

    @Test
    void itShouldFindAllBooksByMatchingISBN() {
        // given
        Category category = new Category("Fantasy");
        categoryRepository.save(category);

        Book book = Book.builder()
                .author("J.K. Rowling")
                .category(category)
                .coverImgUrl("http://link-to-image/harry-potter")
                .isbn("1234-1234-1234-1234")
                .name("Harry Potter and the Philosopher's Stone")
                .price(10.99)
                .build();
        underTest.save(book);

        // when
        String searchQuery = "1234-1234-1234-1234";
        String searchText = "%" + searchQuery.toLowerCase(Locale.ROOT) + "%";
        Specification<Book> spec = (root, query, criteriaBuilder) -> {
            Predicate authorPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("author")), searchText);
            Predicate namePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), searchText);
            Predicate isbnPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("isbn")), searchText);
            return criteriaBuilder.or(isbnPredicate, namePredicate, authorPredicate);
        };

        Page<Book> bookPage = underTest.findAll(spec, PageRequest.of(0, 10));

        // then
        assertThat(bookPage.getTotalElements()).isEqualTo(1);
        assertThat(bookPage.getTotalPages()).isEqualTo(1);
        assertThat(bookPage.getContent().get(0)).isEqualTo(book);
    }

    @Test
    void itShouldFindAllBooksByMatchingName() {
        // given
        Category category = new Category("Fantasy");
        categoryRepository.save(category);

        Book book = Book.builder()
                .author("J.K. Rowling")
                .category(category)
                .coverImgUrl("http://link-to-image/harry-potter")
                .isbn("1234-1234-1234-1234")
                .name("Harry Potter and the Philosopher's Stone")
                .price(10.99)
                .build();
        underTest.save(book);

        // when
        String searchQuery = "Harry Potter";
        String searchText = "%" + searchQuery.toLowerCase(Locale.ROOT) + "%";
        Specification<Book> spec = (root, query, criteriaBuilder) -> {
            Predicate authorPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("author")), searchText);
            Predicate namePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), searchText);
            Predicate isbnPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("isbn")), searchText);
            return criteriaBuilder.or(isbnPredicate, namePredicate, authorPredicate);
        };

        Page<Book> bookPage = underTest.findAll(spec, PageRequest.of(0, 10));

        // then
        assertThat(bookPage.getTotalElements()).isEqualTo(1);
        assertThat(bookPage.getTotalPages()).isEqualTo(1);
        assertThat(bookPage.getContent().get(0)).isEqualTo(book);
    }
}