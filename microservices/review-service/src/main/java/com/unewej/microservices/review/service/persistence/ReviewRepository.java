package com.unewej.microservices.review.service.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ReviewRepository
        extends PagingAndSortingRepository<ReviewEntity, String>, CrudRepository<ReviewEntity, String> {
    List<ReviewEntity> getByProductId(int productId);
    ReviewEntity getByReviewId(int reviewId);
}
