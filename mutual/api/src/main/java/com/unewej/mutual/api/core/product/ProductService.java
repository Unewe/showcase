package com.unewej.mutual.api.core.product;

import org.springframework.web.bind.annotation.*;

public interface ProductService {

    /**
     * Usage: curl $HOST:$PORT/product/1
     * @param id productID
     * @return Product, null if not exists
     */
    @GetMapping(value = "/product/{id}", produces = "application/json")
    Product getProduct(@PathVariable long id);

    /**
     * Usage: curl $HOST:$PORT/product
     * @param product Product
     * @return Product
     */
    @PostMapping(value = "/product", produces = "application/json")
    Product createProduct(Product product);

    /**
     * Usage: curl $HOST:$PORT/product
     * @param product Product
     * @return Product
     */
    @PutMapping(value = "/product", produces = "application/json")
    Product updateProduct(Product product);

    /**
     * Usage: curl $HOST:$PORT/product/id
     * @param id productID
     */
    @DeleteMapping(value = "/product/{id}")
    void deleteProduct(@PathVariable long id);
}
