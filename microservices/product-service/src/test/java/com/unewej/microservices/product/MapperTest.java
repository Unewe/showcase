package com.unewej.microservices.product;

import com.unewej.microservices.product.persistence.ProductEntity;
import com.unewej.microservices.product.service.ProductMapper;
import com.unewej.mutual.api.core.product.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MapperTest {
    @Autowired
    private ProductMapper mapper;

    @Test
    void dtoToEntityTest() {
        var dto = new Product(1, "Name", 3, "Address");
        var entity = mapper.map(dto);

        Assertions.assertEquals(dto.getId(), entity.getId());
        Assertions.assertEquals(dto.getName(), entity.getName());
        Assertions.assertEquals(dto.getWeight(), entity.getWeight());
    }

    @Test
    void entityToDtoTest() {
        var entity = new ProductEntity("Name", 3);
        entity.setId(1L);
        var dto = mapper.map(entity);

        Assertions.assertEquals(dto.getId(), entity.getId());
        Assertions.assertEquals(dto.getName(), entity.getName());
        Assertions.assertEquals(dto.getWeight(), entity.getWeight());
    }
}
