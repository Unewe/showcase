package com.unewej.microservices.product.service.persistence;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    // Pessimistic Lock
    @Query(value = "SELECT p FROM ProductEntity p WHERE p.id = :id")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<ProductEntity> findByIdForUpdate(Long id);
}
