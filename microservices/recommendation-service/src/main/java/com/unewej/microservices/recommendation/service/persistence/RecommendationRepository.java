package com.unewej.microservices.recommendation.service.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface RecommendationRepository
        extends PagingAndSortingRepository<RecommendationEntity, String>, CrudRepository<RecommendationEntity, String> {
    List<RecommendationEntity> findByProductId(int productId);
    Optional<RecommendationEntity> findByRecommendationId(int recommendationId);
}
