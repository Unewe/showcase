package com.unewej.microservices.product.service;

import com.unewej.microservices.product.persistence.ProductRepository;
import com.unewej.mutual.api.core.product.Product;
import com.unewej.mutual.api.core.product.ProductService;
import com.unewej.mutual.api.core.exceptions.InvalidInputException;
import com.unewej.mutual.api.core.exceptions.NotFoundException;
import com.unewej.mutual.util.http.ServiceUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Supplier;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;
    private final ServiceUtil serviceUtil;

    @Override
    public Product getProduct(long id) {
        var result = mapper.map(repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("No Product found for product: %d", id))));
        result.setServiceAddress(serviceUtil.getServiceAddress());
        log.debug("getProduct: found product with id: {}", id);
        return result;
    }

    @Override
    @Transactional
    public Product createProduct(Product product) {
        var entity = repository.save(mapper.map(product));
        var result = mapper.map(entity);
        result.setServiceAddress(serviceUtil.getServiceAddress());
        log.debug("createProduct: created product with id: {}", product.getId());
        return result;
    }

    @Override
    @Transactional
    public Product updateProduct(Product product) {
        return createProduct(product);
    }

    @Override
    public void deleteProduct(long id) {
        log.debug("deleteProduct: delete product with id: {}", id);
        repository.deleteById(id);
    }
}
