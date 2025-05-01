package com.unewej.mutual.api.core.review;

import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ReviewService {

    /**
     * Usage: curl $HOST:$PORT/review/1
     * @param productId product id
     * @return Review, null if not exists
     */
    @GetMapping(value = "/review", produces = "application/json")
    List<Review> getReviews(@RequestParam Long productId);

    @PostMapping(value = "/review", produces = "application/json", consumes = "application/json")
    Review createReview(@RequestBody Review review);

    @PutMapping(value = "/review", produces = "application/json", consumes = "application/json")
    Review updateReview(@RequestBody Review review);

    @DeleteMapping(value = "/review", produces = "application/json")
    void deleteReviews(@RequestParam Long productId);
}
