package com.unewej.microservices.review.service;

import com.unewej.microservices.review.persistence.ReviewEntity;
import com.unewej.mutual.api.core.review.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    @Mappings({@Mapping(target = "serviceAddress", ignore = true)})
    Review map (ReviewEntity entity);

    @Mappings({
            @Mapping(target = "version", ignore = true),
    })
    ReviewEntity map(Review review);
}
