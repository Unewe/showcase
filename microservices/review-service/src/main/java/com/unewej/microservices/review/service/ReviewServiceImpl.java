package com.unewej.microservices.review.service;

import com.unewej.microservices.review.persistence.ReviewRepository;
import com.unewej.mutual.api.core.exceptions.NotFoundException;
import com.unewej.mutual.api.core.review.Review;
import com.unewej.mutual.api.core.review.ReviewService;
import com.unewej.mutual.api.core.exceptions.InvalidInputException;
import com.unewej.mutual.util.http.ServiceUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final ServiceUtil serviceUtil;
    private final ReviewRepository repository;
    private final ReviewMapper mapper;

    @Override
    public List<Review> getReviews(Long productId) {

        if (productId < 1) {
            throw new InvalidInputException("Invalid product id: " + productId);
        }

        var reviews = repository.findByProductId(productId).stream().map(mapper::map).toList();
        reviews.forEach(value -> value.setServiceAddress(serviceUtil.getServiceAddress()));
        log.debug("/product returns the found reviews for product id={}", productId);
        return reviews;
    }

    @Override
    public Review createReview(Review review) {
        var entity = mapper.map(review);
        var result = mapper.map(repository.save(entity));
        result.setServiceAddress(serviceUtil.getServiceAddress());

        return result;
    }

    @Override
    public Review updateReview(Review review) {
        var target = repository.findById(review.getId())
                .orElseThrow(() -> new NotFoundException(String.format("No Review found for id: %s", review.getId())));
        target.setContent(review.getContent());
        target.setSubject(review.getSubject());
        target.setAuthor(review.getAuthor());
        var result = mapper.map(repository.save(target));
        result.setServiceAddress(serviceUtil.getServiceAddress());

        return result;
    }

    @Override
    public void deleteReviews(Long productId) {
        repository.deleteByProductId(productId);
    }
}
