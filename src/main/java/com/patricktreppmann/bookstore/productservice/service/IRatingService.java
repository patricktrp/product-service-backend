package com.patricktreppmann.bookstore.productservice.service;

import com.patricktreppmann.bookstore.productservice.entity.Rating;
import com.patricktreppmann.bookstore.productservice.entity.RatingRequest;
import com.patricktreppmann.bookstore.productservice.error.BookNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRatingService {
    Rating saveRating(RatingRequest ratingRequest, String bookId, String userId) throws BookNotFoundException;
    Page<Rating> fetchRatings(String bookId, Pageable pageable) throws BookNotFoundException;
    void deleteRating(String ratingId);

    Double getAvgRating(long bookId);
}
