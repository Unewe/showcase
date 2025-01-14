package com.unewej.microservices.product.service;

import com.unewej.mutual.api.core.product.Product;
import com.unewej.mutual.api.core.product.ProductService;
import com.unewej.mutual.api.core.exceptions.InvalidInputException;
import com.unewej.mutual.api.core.exceptions.NotFoundException;
import com.unewej.mutual.util.http.ServiceUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ServiceUtil serviceUtil;

    @Override
    public Product getProduct(int id) {
        log.debug("/product returns the found product for product id={}", id);

        if (id < 1) {
            throw new InvalidInputException("Invalid product id: " + id);
        }

        if (id == 13) {
            throw new NotFoundException("No product found for if: " + id);
        }

        return new Product(1, "Product", 1, serviceUtil.getServiceAddress());
    }
}
