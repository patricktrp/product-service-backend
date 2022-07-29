package com.patricktreppmann.bookstore.productservice.service;

import com.patricktreppmann.bookstore.productservice.entity.Book;
import com.patricktreppmann.bookstore.productservice.entity.BookRequest;
import com.patricktreppmann.bookstore.productservice.entity.Category;
import com.patricktreppmann.bookstore.productservice.error.BookNotFoundException;
import com.patricktreppmann.bookstore.productservice.error.CategoryNotFoundException;
import com.patricktreppmann.bookstore.productservice.repository.BookRepository;
import com.patricktreppmann.bookstore.productservice.repository.CategoryRepository;
import com.patricktreppmann.bookstore.productservice.repository.RatingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.Locale;
import java.util.Objects;

@Service
public class BookService implements IBookService{
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final RatingRepository ratingRepository;

    public BookService(BookRepository bookRepository, CategoryRepository categoryRepository, RatingRepository ratingRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.ratingRepository = ratingRepository;
    }

    @Override
    public Page<Book> fetchAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    public Book saveBook(BookRequest bookRequest) throws CategoryNotFoundException {
        Category category = categoryRepository.findById(bookRequest.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("category not found"));
        Book book = Book.builder()
                .name(bookRequest.getName())
                .author(bookRequest.getAuthor())
                .coverImgUrl(bookRequest.getCoverImgUrl())
                .isbn(bookRequest.getIsbn())
                .price(bookRequest.getPrice())
                .category(category)
                .build();
        return bookRepository.save(book);
    }

    @Override
    public Book fetchBookById(Long bookId) throws BookNotFoundException {
        return bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException("book with id " + bookId + " not found"));
    }

    @Override
    public Page<Book> fetchAllBooksWithQuery(Pageable pageable, String search, String categoryName) throws CategoryNotFoundException {
        Category category;
        if (Objects.nonNull(categoryName)) {
            category = categoryRepository.findByNameIgnoreCase(categoryName);
            if (Objects.isNull(category)) {
                throw new CategoryNotFoundException("category with name " + categoryName + " not found");
            }
        } else {
            category = null;
        }

        String searchText = "%" + search.toLowerCase(Locale.ROOT) + "%";

        Specification<Book> spec = (root, query, criteriaBuilder) -> {
            Predicate authorPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("author")), searchText);
            Predicate namePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), searchText);
            Predicate isbnPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("isbn")), searchText);
            Predicate searchPredicate = criteriaBuilder.or(isbnPredicate, namePredicate, authorPredicate);

            if (Objects.nonNull(category)) {
                Predicate categoryPredicate = criteriaBuilder.equal(root.get("category"), category);
                return criteriaBuilder.and(searchPredicate, categoryPredicate);
            }

            return searchPredicate;
        };

        return bookRepository.findAll(spec, pageable);
    }

    @Override
    public void deleteBook(Long bookId) throws BookNotFoundException {
        Book book = fetchBookById(bookId);
        bookRepository.delete(book);
    }
}
