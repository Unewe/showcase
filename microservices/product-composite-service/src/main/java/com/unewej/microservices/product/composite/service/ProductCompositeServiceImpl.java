package com.unewej.microservices.product.composite.service;

import com.unewej.mutual.api.core.composite.product.*;
import com.unewej.mutual.api.core.product.Product;
import com.unewej.mutual.api.core.exceptions.NotFoundException;
import com.unewej.mutual.api.core.recommendation.Recommendation;
import com.unewej.mutual.api.core.review.Review;
import com.unewej.mutual.util.http.ServiceUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductCompositeServiceImpl implements ProductCompositeService {

    private final ServiceUtil serviceUtil;
    private final ProductCompositeIntegration integration;

    @Override
    public ProductAggregate getProduct(int id) {
        var product = integration.getProduct(id);

        if (product == null) {
            throw new NotFoundException("No product found for product id: " + id);
        }

        List<Recommendation> recommendations = integration.getRecommendations(id);
        List<Review> reviews = integration.getReviews(id);

        return createProductAggregate(product, recommendations, reviews);
    }

    private ProductAggregate createProductAggregate(
            Product product,
            List<Recommendation> recommendations,
            List<Review> reviews
    ) {
        int productId = product.getId();
        String productName = product.getName();
        int productWeight = product.getWeight();

        List<RecommendationSummary> recommendationSummaries = recommendations == null
                ? List.of()
                : recommendations
                .stream()
                .map(r -> new RecommendationSummary(r.getId(), r.getAuthor(), r.getRate(), r.getContent()))
                .toList();

        List<ReviewSummary> reviewSummaries = reviews == null
                ? List.of()
                : reviews
                .stream()
                .map(r -> new ReviewSummary(r.getReviewId(), r.getAuthor(), r.getSubject(), r.getContent()))
                .toList();

        String productAddress = product.getServiceAddress();
        String recommendationAddress = recommendations == null || recommendations.isEmpty()
                ? null
                : recommendations.get(0).getServiceAddress();
        String reviewAddress = reviews == null || reviews.isEmpty()
                ? null
                : reviews.get(0).getServiceAddress();

        return new ProductAggregate(
                productId,
                productName,
                productWeight,
                recommendationSummaries,
                reviewSummaries,
                new ServiceAddresses(serviceUtil.getServiceAddress(), productAddress, recommendationAddress, reviewAddress)
        );
    }
}
