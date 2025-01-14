package com.unewej.mutual.api.core.recommendation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface RecommendationService {

    /**
     * Usage: curl $HOST:$PORT/recommendation/1
     * @param productId product id
     * @return Recommendation, null if not exists
     */
    @GetMapping(value = "/recommendation", produces = "application/json")
    List<Recommendation> getRecommendations(@RequestParam int productId);
}
