package com.unewej.microservices.product;

import com.unewej.microservices.product.service.persistence.ProductEntity;
import com.unewej.microservices.product.service.persistence.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.LongStream;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PersistenceTests extends PostgresqlDbTestBase {
    @Autowired
    private ProductRepository repository;

    private ProductEntity savedEntity;

    @BeforeEach
    public void setupDb() {
        repository.deleteAll();
        ProductEntity entity = new ProductEntity("1", 1);
        savedEntity = repository.save(entity);

        assertEqualsRecommendation(entity, savedEntity);
    }

    private void assertEqualsRecommendation(ProductEntity a, ProductEntity b) {
        Assertions.assertEquals(a.getName(), b.getName());
        Assertions.assertEquals(a.getId(), b.getId());
        Assertions.assertEquals(a.getWeight(), b.getWeight());
    }

    @Test
    public void create() {
        ProductEntity entity = new ProductEntity("2", 2);
        repository.save(entity);
        ProductEntity saved = repository.findById(entity.getId()).get();
        assertEqualsRecommendation(entity, saved);

        Assertions.assertEquals(2, repository.count());
    }

    @Test
    public void update() {
        savedEntity.setName("newName");
        repository.save(savedEntity);
        ProductEntity foundEntity = repository.findById(savedEntity.getId()).get();

        Assertions.assertEquals(1, foundEntity.getVersion());
        assertEqualsRecommendation(savedEntity, foundEntity);
    }

    @Test
    public void delete() {
        repository.delete(savedEntity);
        Assertions.assertFalse(repository.existsById(savedEntity.getId()));
    }

    @Test
    public void paging() {
        repository.deleteAll();

        List<ProductEntity> newProducts = LongStream.rangeClosed(1001, 1010)
                .mapToObj((i) -> new ProductEntity(String.valueOf(i), (int) i))
                .toList();

        repository.saveAll(newProducts);
        Pageable nextPage = PageRequest.of(0, 4, Sort.Direction.ASC, "id");
        nextPage = testNextPage(nextPage, "[6, 7, 8, 9]", true);
        nextPage = testNextPage(nextPage, "[10, 11, 12, 13]", true);
        nextPage = testNextPage(nextPage, "[14, 15]", false);
    }

    private Pageable testNextPage(Pageable nextPage, String expectedIds, Boolean expectNextPage) {
        Page<ProductEntity> reviewPage = repository.findAll(nextPage);
        Assertions.assertEquals(expectedIds, reviewPage.stream().map(ProductEntity::getId).toList().toString());
        Assertions.assertEquals(expectNextPage, reviewPage.hasNext());
        return reviewPage.nextPageable();
    }


    @Test
    public void optimisticLockError() {
        var review1 = repository.findById(savedEntity.getId()).get();
        var review2 = repository.findById(savedEntity.getId()).get();

        review1.setName("231");
        repository.save(review1);

        Assertions.assertThrows(OptimisticLockingFailureException.class, () -> {
            review2.setName("890");
            repository.save(review2);
        });

        var review = repository.findById(savedEntity.getId()).get();
        Assertions.assertEquals(1, review.getVersion());
        Assertions.assertEquals("231", review.getName());
    }
}
