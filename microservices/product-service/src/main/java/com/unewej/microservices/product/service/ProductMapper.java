package com.unewej.microservices.product.service;

import com.unewej.microservices.product.persistence.ProductEntity;
import com.unewej.mutual.api.core.product.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mappings({@Mapping(target = "serviceAddress", ignore = true)})
    Product map(ProductEntity value);

    @Mappings({
            @Mapping(target = "version", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true)
    })
    ProductEntity map(Product value);
}
