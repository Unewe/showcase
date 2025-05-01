package com.unewej.microservices.recommendation.service;

import com.unewej.microservices.recommendation.persistence.RecommendationEntity;
import com.unewej.mutual.api.core.recommendation.Recommendation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface RecommendationMapper {
    @Mappings({@Mapping(target = "serviceAddress", ignore = true)})
    Recommendation map (RecommendationEntity entity);

    @Mappings({@Mapping(target = "version", ignore = true)})
    RecommendationEntity map(Recommendation review);
}
