package com.patricktreppmann.bookstore.productservice.service;

import com.patricktreppmann.bookstore.productservice.entity.Book;
import com.patricktreppmann.bookstore.productservice.entity.BookRequest;
import com.patricktreppmann.bookstore.productservice.error.BookNotFoundException;
import com.patricktreppmann.bookstore.productservice.error.CategoryNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBookService {
    Page<Book> fetchAllBooks(Pageable pageable);
    Page<Book> fetchAllBooksWithQuery(Pageable pageable, String search, String categoryName) throws CategoryNotFoundException;
    Book saveBook(BookRequest book) throws CategoryNotFoundException;
    Book fetchBookById(Long bookId) throws BookNotFoundException;
    void deleteBook(Long bookId) throws BookNotFoundException;
}
