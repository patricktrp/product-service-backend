package com.patricktreppmann.bookstore.productservice.controller;

import com.patricktreppmann.bookstore.productservice.entity.PageResponse;
import com.patricktreppmann.bookstore.productservice.entity.Rating;
import com.patricktreppmann.bookstore.productservice.entity.RatingRequest;
import com.patricktreppmann.bookstore.productservice.error.BookNotFoundException;
import com.patricktreppmann.bookstore.productservice.service.IRatingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/api/v1/books/{bookId}")
public class RatingController {
    private final IRatingService ratingService;

    public RatingController(IRatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping("/ratings")
    public PageResponse<Rating> fetchRatings(
            @PathVariable String bookId,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10")
            @Min(value = 10, message = "page size must be 10 at minimum")
            @Max(value = 100, message = "page size cant exceed 100") int size) throws BookNotFoundException {
        PageRequest pageRequest = PageRequest.of(page, size);

        Page<Rating> fetchedRatings = ratingService.fetchRatings(bookId, pageRequest);
        return new PageResponse<>(fetchedRatings);
    }

    @GetMapping("/ratings/{ratingId}")
    public Rating fetchRatingById(@PathVariable String bookId, @PathVariable String ratingId) {
        return null;
    }

    @GetMapping("/ratings/avg")
    public Double getAvgRating(@PathVariable String bookId) {
        return ratingService.getAvgRating(Long.parseLong(bookId));
    }

    @PostMapping("/ratings")
    public ResponseEntity<Object> createRating(@PathVariable String bookId, @RequestBody @Valid RatingRequest ratingRequest, Authentication authentication) throws BookNotFoundException {
        String userId = authentication.getName();
        Rating savedRating = ratingService.saveRating(ratingRequest, bookId, userId);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{ratingId}").buildAndExpand(savedRating.getRatingId()).toUri()).build();
    }

    @PutMapping("/ratings/{ratingId}")
    public Rating updateRating(@PathVariable String bookId, @PathVariable String ratingId) {
        return null;
    }

    @DeleteMapping("/ratings/{ratingId}")
    public ResponseEntity<String> deleteRating(@PathVariable String bookId, @PathVariable String ratingId) {
        // TODO - ONLY DELETE IF RATING_ID AND USER_ID MATCH!
        ratingService.deleteRating(ratingId);
        return ResponseEntity.ok().body("book with id " + ratingId + " successfully deleted");
    }
}
