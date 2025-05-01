package com.unewej.microservices.recommendation.service;

import com.unewej.microservices.recommendation.persistence.RecommendationRepository;
import com.unewej.mutual.api.core.exceptions.NotFoundException;
import com.unewej.mutual.api.core.recommendation.Recommendation;
import com.unewej.mutual.api.core.recommendation.RecommendationService;
import com.unewej.mutual.api.core.exceptions.InvalidInputException;
import com.unewej.mutual.util.http.ServiceUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RecommendationServiceImpl implements RecommendationService {
    private final RecommendationRepository repository;
    private final RecommendationMapper mapper;
    private final ServiceUtil serviceUtil;

    @Override
    public List<Recommendation> getRecommendations(Long productId) {
        var recommendations = repository.findByProductId(productId);
        var result = recommendations.stream().map(mapper::map).toList();
        result.forEach(value -> value.setServiceAddress(serviceUtil.getServiceAddress()));
        log.debug("/product returns the found product for product id={}", productId);

        return result;
    }

    @Override
    public Recommendation createRecommendation(Recommendation recommendation) {
        var result = mapper.map(repository.save(mapper.map(recommendation)));
        result.setServiceAddress(serviceUtil.getServiceAddress());

        return result;
    }

    @Override
    public Recommendation updateRecommendation(Recommendation recommendation) {
        var target = repository.findById(recommendation.getId())
                .orElseThrow(() -> new NotFoundException(String.format("No Recommendation found for id: %s", recommendation.getId())));
        target.setAuthor(recommendation.getAuthor());
        target.setContent(recommendation.getContent());
        target.setRate(recommendation.getRate());

        var result = mapper.map(repository.save(target));
        result.setServiceAddress(serviceUtil.getServiceAddress());
        return result;
    }

    @Override
    public void deleteRecommendations(Long productId) {
        repository.deleteByProductId(productId);
    }
}
