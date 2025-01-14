package com.unewej.microservices.recommendation.service;

import com.unewej.mutual.api.core.recommendation.Recommendation;
import com.unewej.mutual.api.core.recommendation.RecommendationService;
import com.unewej.mutual.api.core.exceptions.InvalidInputException;
import com.unewej.mutual.util.http.ServiceUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RecommendationServiceImpl implements RecommendationService {

    private final ServiceUtil serviceUtil;

    @Override
    public List<Recommendation> getRecommendations(int productId) {
        log.debug("/product returns the found product for product id={}", productId);

        if (productId < 1) {
            throw new InvalidInputException("Invalid product id: " + productId);
        }

        if (productId == 13) {
            return new ArrayList<>();
        }

        return List.of(
                new Recommendation(1, productId, "Author", 5, "qwer", serviceUtil.getServiceAddress()),
                new Recommendation(2, productId, "Author", 4, "asdf", serviceUtil.getServiceAddress()),
                new Recommendation(3, productId, "Author", 3, "zxcv", serviceUtil.getServiceAddress())
        );
    }
}
