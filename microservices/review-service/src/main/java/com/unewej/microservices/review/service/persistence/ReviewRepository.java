package com.unewej.microservices.review.service.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository
        extends PagingAndSortingRepository<ReviewEntity, String>, CrudRepository<ReviewEntity, String> {
    List<ReviewEntity> findByProductId(int productId);
    Optional<ReviewEntity> findByReviewId(int reviewId);
}
