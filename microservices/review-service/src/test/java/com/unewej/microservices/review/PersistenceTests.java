package com.unewej.microservices.review;

import com.unewej.microservices.review.service.persistence.ReviewEntity;
import com.unewej.microservices.review.service.persistence.ReviewRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

@DataMongoTest
public class PersistenceTests extends MongoDbTestBase {
    @Autowired
    private ReviewRepository reviewRepository;

    private ReviewEntity savedEntity;

    @BeforeEach
    public void setupDb() {
        reviewRepository.deleteAll();
        ReviewEntity entity = new ReviewEntity(1, 1, "1", "1", "1");
        savedEntity = reviewRepository.save(entity);

        assertEqualsReview(entity, savedEntity);
    }

    private void assertEqualsReview(ReviewEntity a, ReviewEntity b) {
        Assertions.assertEquals(a.getReviewId(), b.getReviewId());
        Assertions.assertEquals(a.getId(), b.getId());
        Assertions.assertEquals(a.getSubject(), b.getSubject());
        Assertions.assertEquals(a.getAuthor(), b.getAuthor());
        Assertions.assertEquals(a.getContent(), b.getContent());
    }

    @Test
    public void create() {
        ReviewEntity entity = new ReviewEntity(2, 1, "2", "2", "2");
        reviewRepository.save(entity);
        ReviewEntity saved = reviewRepository.findById(entity.getId()).get();
        assertEqualsReview(entity, saved);

        Assertions.assertEquals(2, reviewRepository.count());
    }
}
