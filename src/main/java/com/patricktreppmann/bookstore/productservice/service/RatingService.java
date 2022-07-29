package com.patricktreppmann.bookstore.productservice.service;

import com.patricktreppmann.bookstore.productservice.entity.Book;
import com.patricktreppmann.bookstore.productservice.entity.Rating;
import com.patricktreppmann.bookstore.productservice.entity.RatingRequest;
import com.patricktreppmann.bookstore.productservice.error.BookNotFoundException;
import com.patricktreppmann.bookstore.productservice.repository.BookRepository;
import com.patricktreppmann.bookstore.productservice.repository.RatingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RatingService implements IRatingService{
    private final RatingRepository ratingRepository;
    private final IBookService bookService;

    public RatingService(RatingRepository ratingRepository, BookRepository bookRepository, IBookService bookService) {
        this.ratingRepository = ratingRepository;
        this.bookService = bookService;
    }

    @Override
    public Rating saveRating(RatingRequest ratingRequest, String bookId, String userId) throws BookNotFoundException {
        Long bookIdLong = Long.parseLong(bookId);
        Book book = bookService.fetchBookById(bookIdLong);

        Rating rating = new Rating();
        LocalDateTime createdAt = LocalDateTime.now();

        rating.setDescription(ratingRequest.getDescription());
        rating.setHeading(ratingRequest.getHeading());
        rating.setStars(ratingRequest.getStars());
        rating.setCreated(createdAt);
        rating.setUserId(userId);
        rating.setLastUpdated(createdAt);
        rating.setBook(book);

        return ratingRepository.save(rating);
    }

    @Override
    public Page<Rating> fetchRatings(String bookId, Pageable pageable) throws BookNotFoundException {
        Long bookIdLong = Long.parseLong(bookId);
        Book book = bookService.fetchBookById(bookIdLong);
        return ratingRepository.findAllByBook(book, pageable);
    }

    @Override
    public void deleteRating(String ratingId) {

    }

    @Override
    public Double getAvgRating(long bookId) {
        return ratingRepository.getAverageRating(bookId);
    }
}
