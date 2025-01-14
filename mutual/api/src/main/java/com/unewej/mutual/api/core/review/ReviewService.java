package com.unewej.mutual.api.core.review;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ReviewService {

    /**
     * Usage: curl $HOST:$PORT/review/1
     * @param productId product id
     * @return Review, null if not exists
     */
    @GetMapping(value = "/review", produces = "application/json")
    List<Review> getReviews(@RequestParam int productId);
}
