package com.patricktreppmann.bookstore.productservice.repository;

import com.patricktreppmann.bookstore.productservice.entity.Book;
import com.patricktreppmann.bookstore.productservice.entity.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    Page<Rating> findAllByBook(Book book, Pageable pageable);

    @Query(value = "SELECT AVG(r.stars) FROM rating r WHERE r.book_id = ?1" , nativeQuery = true)
    Double getAverageRating(Long bookId);
}
