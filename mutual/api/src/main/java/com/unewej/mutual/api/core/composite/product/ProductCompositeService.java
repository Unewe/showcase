package com.unewej.mutual.api.core.composite.product;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface ProductCompositeService {
    /**
     * curl $HOST:$PORT/product-composite/1
     * @param id product id
     * @return product or throw not found
     */
    @GetMapping(value = "/product-composite/{id}", produces = "application/json")
    ProductAggregate getProduct(@PathVariable int id);
}
