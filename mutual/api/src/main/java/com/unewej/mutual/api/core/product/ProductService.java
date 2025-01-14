package com.unewej.mutual.api.core.product;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface ProductService {

    /**
     * Usage: curl $HOST:$PORT/product/1
     * @param id productID
     * @return Product, null if not exists
     */
    @GetMapping(value = "/product/{id}", produces = "application/json")
    Product getProduct(@PathVariable int id);
}
