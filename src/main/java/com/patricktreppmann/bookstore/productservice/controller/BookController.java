package com.patricktreppmann.bookstore.productservice.controller;

import com.patricktreppmann.bookstore.productservice.entity.Book;
import com.patricktreppmann.bookstore.productservice.entity.BookRequest;
import com.patricktreppmann.bookstore.productservice.entity.PageResponse;
import com.patricktreppmann.bookstore.productservice.error.BookNotFoundException;
import com.patricktreppmann.bookstore.productservice.error.CategoryNotFoundException;
import com.patricktreppmann.bookstore.productservice.error.IllegalSortDirectionException;
import com.patricktreppmann.bookstore.productservice.error.IllegalSortParamException;
import com.patricktreppmann.bookstore.productservice.service.IBookService;
import com.patricktreppmann.bookstore.productservice.validation.NotBlankIfPresent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Objects;


@RestController
@Validated
@RequestMapping("/api/v1")
public class BookController {
    private final IBookService bookService;

    public BookController(IBookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public PageResponse<Book> fetchAllBooks (
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10")
                @Min(value = 10, message = "page size must be 10 at minimum")
                @Max(value = 100, message = "page size cant exceed 100") int size,
            @RequestParam(required = false)
                @NotBlankIfPresent(message = "search must not be empty if set") String search,
            @RequestParam(required = false)
                @NotBlankIfPresent(message = "category must not be empty if set") String category,
            @RequestParam(required = false, defaultValue = "name") String sort,
            @RequestParam(required = false, defaultValue = "asc") String order
            ) throws IllegalSortParamException, CategoryNotFoundException, IllegalSortDirectionException {

        if (!Book.SORTABLE_FIELDS.contains(sort)) {
            throw new IllegalSortParamException("books are only sortable on " + Book.SORTABLE_FIELDS);
        }

        Sort.Direction sortDirection;
        if (order.equals("desc")) {
            sortDirection = Sort.Direction.DESC;
        } else if (order.equals("asc")) {
            sortDirection = Sort.Direction.ASC;
        } else {
            throw new IllegalSortDirectionException("you're only allowed to sort in ascending [asc] oder descending [desc] order");
        }

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortDirection, sort));


        Page<Book> fetchedBooks;
        if (Objects.isNull(search) || search.isBlank()) {
            fetchedBooks = bookService.fetchAllBooks(pageRequest);
        } else {
            fetchedBooks = bookService.fetchAllBooksWithQuery(pageRequest, search, category);
        }

        return new PageResponse<>(fetchedBooks);
    }

    @GetMapping("/books/{bookId}")
    public Book fetchBookById(@PathVariable Long bookId) throws BookNotFoundException {
        return bookService.fetchBookById(bookId);
    }

    @PostMapping("/books")
    public ResponseEntity<Book> saveBook(@RequestBody @Valid BookRequest bookRequest) throws CategoryNotFoundException {
        Book savedBook = bookService.saveBook(bookRequest);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{bookId}").buildAndExpand(savedBook.getBookId()).toUri()).build();
    }

    @DeleteMapping("/books/{bookId}")
    public ResponseEntity<String> deleteBook(@PathVariable Long bookId) throws BookNotFoundException {
        bookService.deleteBook(bookId);
        return ResponseEntity.ok().body("book with id " + bookId + " successfully deleted");
    }

    @PutMapping("/books/{bookId}")
    public ResponseEntity<String> updateBook(@RequestBody Book book) {
        return ResponseEntity.ok().body("update successful");
    }
}
