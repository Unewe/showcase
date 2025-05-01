package com.unewej.microservices.review.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository
        extends PagingAndSortingRepository<ReviewEntity, String>, CrudRepository<ReviewEntity, String> {
    List<ReviewEntity> findByProductId(Long productId);
    void deleteByProductId(Long productId);
}
