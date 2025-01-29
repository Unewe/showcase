package com.unewej.microservices.recommendation;

import com.unewej.microservices.recommendation.service.persistence.RecommendationEntity;
import com.unewej.microservices.recommendation.service.persistence.RecommendationRepository;
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
    private RecommendationRepository repository;

    private RecommendationEntity savedEntity;

    @BeforeEach
    public void setupDb() {
        repository.deleteAll();
        RecommendationEntity entity = new RecommendationEntity(1, 1,"1", 1, "1");
        savedEntity = repository.save(entity);

        assertEqualsRecommendation(entity, savedEntity);
    }

    private void assertEqualsRecommendation(RecommendationEntity a, RecommendationEntity b) {
        Assertions.assertEquals(a.getRecommendationId(), b.getRecommendationId());
        Assertions.assertEquals(a.getId(), b.getId());
        Assertions.assertEquals(a.getRate(), b.getRate());
        Assertions.assertEquals(a.getAuthor(), b.getAuthor());
        Assertions.assertEquals(a.getContent(), b.getContent());
    }

    @Test
    public void create() {
        RecommendationEntity entity = new RecommendationEntity(2, 2,"2", 2, "2");
        repository.save(entity);
        RecommendationEntity saved = repository.findById(entity.getId()).get();
        assertEqualsRecommendation(entity, saved);

        Assertions.assertEquals(2, repository.count());
    }

    @Test
    public void update() {
        savedEntity.setContent("newContent");
        repository.save(savedEntity);
        RecommendationEntity foundEntity = repository.findById(savedEntity.getId()).get();

        Assertions.assertEquals(1, foundEntity.getVersion());
        assertEqualsRecommendation(savedEntity, foundEntity);
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
        assertEqualsRecommendation(savedEntity, reviews.get(0));
    }

    @Test
    public void duplicateError() {
        Assertions.assertThrows(DuplicateKeyException.class, () -> {
            var recommendation = new RecommendationEntity(savedEntity.getRecommendationId(), 1, "1", 1, "1");
            repository.save(recommendation);
        });
    }

    @Test
    public void paging() {
        repository.deleteAll();

        List<RecommendationEntity> newReviews = IntStream.rangeClosed(1001, 1010)
                .mapToObj((i) -> new RecommendationEntity(i, 1, "1", 1, String.valueOf(i)))
                .toList();

        repository.saveAll(newReviews);
        Pageable nextPage = PageRequest.of(0, 4, Sort.Direction.ASC, "recommendationId");
        nextPage = testNextPage(nextPage, "[1001, 1002, 1003, 1004]", true);
        nextPage = testNextPage(nextPage, "[1005, 1006, 1007, 1008]", true);
        nextPage = testNextPage(nextPage, "[1009, 1010]", false);
    }

    private Pageable testNextPage(Pageable nextPage, String expectedIds, Boolean expectNextPage) {
        Page<RecommendationEntity> reviewPage = repository.findAll(nextPage);
        Assertions.assertEquals(expectedIds, reviewPage.stream().map(RecommendationEntity::getRecommendationId).toList().toString());
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
