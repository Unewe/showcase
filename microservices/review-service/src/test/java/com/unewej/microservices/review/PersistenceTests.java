package com.unewej.microservices.review;

import com.unewej.microservices.review.persistence.ReviewEntity;
import com.unewej.microservices.review.persistence.ReviewRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.IntStream;

@DataMongoTest
public class PersistenceTests extends MongoDbTestBase {
    @Autowired
    private ReviewRepository repository;

    private ReviewEntity savedEntity;

    @BeforeEach
    public void setupDb() {
        repository.deleteAll();
        ReviewEntity entity = new ReviewEntity(1, 1, "1", "1", "1");
        savedEntity = repository.save(entity);

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
        repository.save(entity);
        ReviewEntity saved = repository.findById(entity.getId()).get();
        assertEqualsReview(entity, saved);

        Assertions.assertEquals(2, repository.count());
    }

    @Test
    public void update() {
        savedEntity.setContent("newContent");
        repository.save(savedEntity);
        ReviewEntity foundEntity = repository.findById(savedEntity.getId()).get();

        Assertions.assertEquals(1, foundEntity.getVersion());
        assertEqualsReview(savedEntity, foundEntity);
    }

    @Test
    public void delete() {
        repository.delete(savedEntity);
        Assertions.assertFalse(repository.existsById(savedEntity.getId()));
    }

    @Test
    public void findByProductId() {
        var reviews = repository.findByProductId(savedEntity.getProductId());
        Assertions.assertEquals(1, reviews.size());
        assertEqualsReview(savedEntity, reviews.get(0));
    }

    @Test
    public void duplicateError() {
        Assertions.assertThrows(DuplicateKeyException.class, () -> {
            var review = new ReviewEntity(savedEntity.getReviewId(), 1, "1", "1", "1");
            repository.save(review);
        });
    }

    @Test
    public void paging() {
        repository.deleteAll();

        List<ReviewEntity> newReviews = IntStream.rangeClosed(1001, 1010)
                .mapToObj((i) -> new ReviewEntity(i, 1, "1", "1", String.valueOf(i)))
                .toList();

        repository.saveAll(newReviews);
        Pageable nextPage = PageRequest.of(0, 4, Sort.Direction.ASC, "reviewId");
        nextPage = testNextPage(nextPage, "[1001, 1002, 1003, 1004]", true);
        nextPage = testNextPage(nextPage, "[1005, 1006, 1007, 1008]", true);
        nextPage = testNextPage(nextPage, "[1009, 1010]", false);
    }

    private Pageable testNextPage(Pageable nextPage, String expectedIds, Boolean expectNextPage) {
        Page<ReviewEntity> reviewPage = repository.findAll(nextPage);
        Assertions.assertEquals(expectedIds, reviewPage.stream().map(ReviewEntity::getReviewId).toList().toString());
        Assertions.assertEquals(expectNextPage, reviewPage.hasNext());
        return reviewPage.nextPageable();
    }


    @Test
    public void optimisticLockError() {
        var review1 = repository.findById(savedEntity.getId()).get();
        var review2 = repository.findById(savedEntity.getId()).get();

        review1.setContent("231");
        repository.save(review1);

        Assertions.assertThrows(OptimisticLockingFailureException.class, () -> {
            review2.setContent("890");
            repository.save(review2);
        });

        var review = repository.findById(savedEntity.getId()).get();
        Assertions.assertEquals(1, review.getVersion());
        Assertions.assertEquals("231", review.getContent());
    }
}
