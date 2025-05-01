package com.unewej.microservices.product.composite.service;

import com.unewej.mutual.api.core.composite.product.ProductAggregate;
import com.unewej.mutual.api.core.composite.product.RecommendationSummary;
import com.unewej.mutual.api.core.composite.product.ReviewSummary;
import com.unewej.mutual.api.core.product.Product;
import com.unewej.mutual.api.core.recommendation.Recommendation;
import com.unewej.mutual.api.core.review.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ProductAggregateMapper {
    @Mappings({
            @Mapping(target = "reviews", ignore = true),
            @Mapping(target = "recommendations", ignore = true),
            @Mapping(target = "serviceAddresses", ignore = true)
    })
    ProductAggregate mapProduct (Product entity);

    @Mappings({
            @Mapping(target = "serviceAddress", ignore = true)
    })
    Product mapProduct(ProductAggregate review);

    @Mappings({})
    ReviewSummary mapReview (Review entity);

    @Mappings({
            @Mapping(target = "serviceAddress", ignore = true)
    })
    Review mapReview(ReviewSummary review);

    @Mappings({})
    RecommendationSummary mapRecommendation (Recommendation entity);

    @Mappings({
            @Mapping(target = "serviceAddress", ignore = true)
    })
    Recommendation mapRecommendation(RecommendationSummary review);
}
