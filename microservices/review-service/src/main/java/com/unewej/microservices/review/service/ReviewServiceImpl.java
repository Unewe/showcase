package com.unewej.microservices.review.service;

import com.unewej.mutual.api.core.review.Review;
import com.unewej.mutual.api.core.review.ReviewService;
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
public class ReviewServiceImpl implements ReviewService {

    private final ServiceUtil serviceUtil;

    @Override
    public List<Review> getReviews(int productId) {
        log.debug("/product returns the found product for product id={}", productId);

        if (productId < 1) {
            throw new InvalidInputException("Invalid product id: " + productId);
        }

        if (productId == 13) {
            return new ArrayList<>();
        }

        return List.of(
                new Review(1, productId, "Author 1", "Subject 1", "Content 1", serviceUtil.getServiceAddress()),
                new Review(2, productId, "Author 2", "Subject 2", "Content 2", serviceUtil.getServiceAddress()),
                new Review(3, productId, "Author 3", "Subject 3", "Content 3", serviceUtil.getServiceAddress())
        );
    }
}
