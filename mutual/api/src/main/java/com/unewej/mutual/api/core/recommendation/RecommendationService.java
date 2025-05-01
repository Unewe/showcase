package com.unewej.mutual.api.core.recommendation;

import com.unewej.mutual.api.core.review.Review;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface RecommendationService {

    /**
     * Usage: curl $HOST:$PORT/recommendation/1
     * @param productId product id
     * @return Recommendation, null if not exists
     */
    @GetMapping(value = "/recommendation", produces = "application/json")
    List<Recommendation> getRecommendations(@RequestParam Long productId);
    @PostMapping(value = "/recommendation", produces = "application/json", consumes = "application/json")
    Recommendation createRecommendation(@RequestBody Recommendation review);

    @PutMapping(value = "/recommendation", produces = "application/json", consumes = "application/json")
    Recommendation updateRecommendation(@RequestBody Recommendation review);

    @DeleteMapping(value = "/recommendation", produces = "application/json")
    void deleteRecommendations(@RequestParam Long productId);
}
