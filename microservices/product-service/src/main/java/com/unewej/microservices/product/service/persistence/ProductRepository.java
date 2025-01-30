package com.unewej.microservices.product.service.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends
        PagingAndSortingRepository<ProductEntity, Long>, CrudRepository<ProductEntity, Long>  {
}
