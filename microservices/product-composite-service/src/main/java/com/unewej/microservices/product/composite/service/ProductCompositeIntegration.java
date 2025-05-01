package com.unewej.microservices.product.composite.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unewej.mutual.api.core.exceptions.InvalidInputException;
import com.unewej.mutual.api.core.exceptions.NotFoundException;
import com.unewej.mutual.api.core.product.Product;
import com.unewej.mutual.api.core.recommendation.Recommendation;
import com.unewej.mutual.api.core.review.Review;
import com.unewej.mutual.util.http.HttpErrorInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@Slf4j
public class ProductCompositeIntegration {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String productServiceUrl;
    private final String recommendationServiceUrl;
    private final String reviewServiceUrl;


    public ProductCompositeIntegration(
            RestTemplate restTemplate,
            ObjectMapper objectMapper,
            @Value("${app.product-service.host}") String productServiceHost,
            @Value("${app.product-service.port}") String productServicePort,
            @Value("${app.recommendation-service.host}") String recommendationServiceHost,
            @Value("${app.recommendation-service.port}") String recommendationServicePort,
            @Value("${app.review-service.host}") String reviewServiceHost,
            @Value("${app.review-service.port}") String reviewServicePort
    ) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.productServiceUrl = "http://" + productServiceHost + ":" + productServicePort + "/product";
        this.recommendationServiceUrl = "http://" + recommendationServiceHost + ":" + recommendationServicePort + "/recommendation";
        this.reviewServiceUrl = "http://" + reviewServiceHost + ":" + reviewServicePort + "/review";
    }

    public Product getProduct(long productId) {
        try {
            String url = productServiceUrl + "/" + productId;
            log.debug("Will call getProduct API on URL: {}", url);
            Product product = restTemplate.getForObject(url, Product.class);
            log.debug("Found the product with id: {}", product.getId());

            return product;
        } catch (HttpClientErrorException e) {
            switch (HttpStatus.resolve(e.getStatusCode().value())) {
                case NOT_FOUND -> throw new NotFoundException(getErrorMessage(e));
                case UNPROCESSABLE_ENTITY -> throw new InvalidInputException(getErrorMessage(e));
                default -> {
                    log.warn("Got an unexpected HTTP error: {}, will rethrow it", e.getStatusCode());
                    log.warn("Error bod: {}", e.getResponseBodyAsString());
                    throw e;
                }
            }
        }
    }

    public Product createProduct(Product value) {
        try {
            String url = productServiceUrl;
            log.debug("Will call postProduct API on URL: {}", url);
            Product product = restTemplate.postForObject(url, value, Product.class);
            log.debug("Created the product with id: {}", product.getId());

            return product;
        } catch (HttpClientErrorException e) {
            if (HttpStatus.resolve(e.getStatusCode().value()) == HttpStatus.UNPROCESSABLE_ENTITY) {
                throw new InvalidInputException(getErrorMessage(e));
            }
            log.warn("Got an unexpected HTTP error: {}, will rethrow it", e.getStatusCode());
            log.warn("Error bod: {}", e.getResponseBodyAsString());
            throw e;
        }
    }

    public Product updateProduct(Product value) {
        try {
            String url = productServiceUrl;
            log.debug("Will call putProduct API on URL: {}", url);
            restTemplate.put(url, value, Product.class);
            log.debug("Update the product with id: {}", value.getId());

            return value;
        } catch (HttpClientErrorException e) {
            switch (HttpStatus.resolve(e.getStatusCode().value())) {
                case NOT_FOUND -> throw new NotFoundException(getErrorMessage(e));
                case UNPROCESSABLE_ENTITY -> throw new InvalidInputException(getErrorMessage(e));
                default -> {
                    log.warn("Got an unexpected HTTP error: {}, will rethrow it", e.getStatusCode());
                    log.warn("Error bod: {}", e.getResponseBodyAsString());
                    throw e;
                }
            }
        }
    }

    public void deleteProduct(long productId) {
        try {
            String url = productServiceUrl + "/" + productId;
            log.debug("Will call deleteProduct API on URL: {}", url);
            restTemplate.delete(url);
            log.debug("Delete the product with id: {}", productId);
        } catch (HttpClientErrorException e) {
            switch (HttpStatus.resolve(e.getStatusCode().value())) {
                case NOT_FOUND -> throw new NotFoundException(getErrorMessage(e));
                case UNPROCESSABLE_ENTITY -> throw new InvalidInputException(getErrorMessage(e));
                default -> {
                    log.warn("Got an unexpected HTTP error: {}, will rethrow it", e.getStatusCode());
                    log.warn("Error bod: {}", e.getResponseBodyAsString());
                    throw e;
                }
            }
        }
    }

    public List<Recommendation> getRecommendations(long productId) {
        try {
            String url = this.recommendationServiceUrl + "?productId=" + productId;
            log.debug("Will call recommendation service API on URL: {}", url);
            List<Recommendation> recommendations = restTemplate
                    .exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Recommendation>>() {})
                    .getBody();
            log.debug("Found {} recommendations for product id: {}", recommendations.size(), productId);
            return recommendations;
        } catch (Exception e) {
            log.debug("Got an exception while requesting recommendations, return zero recommendations: {}", e.getMessage());
            return List.of();
        }
    }

    public Recommendation createRecommendation(Recommendation value) {
        try {
            String url = recommendationServiceUrl;
            log.debug("Will call postRecommendation API on URL: {}", url);
            Recommendation recommendation = restTemplate.postForObject(url, value, Recommendation.class);
            log.debug("Created the recommendation with id: {}", recommendation.getRecommendationId());

            return recommendation;
        } catch (HttpClientErrorException e) {
            if (HttpStatus.resolve(e.getStatusCode().value()) == HttpStatus.UNPROCESSABLE_ENTITY) {
                throw new InvalidInputException(getErrorMessage(e));
            }
            log.warn("Got an unexpected HTTP error: {}, will rethrow it", e.getStatusCode());
            log.warn("Error bod: {}", e.getResponseBodyAsString());
            throw e;
        }
    }

    public Recommendation updateRecommendation(Recommendation value) {
        try {
            String url = recommendationServiceUrl;
            log.debug("Will call putRecommendation API on URL: {}", url);
            restTemplate.put(url, value, Recommendation.class);
            log.debug("Update the recommendation with id: {}", value.getId());

            return value;
        } catch (HttpClientErrorException e) {
            switch (HttpStatus.resolve(e.getStatusCode().value())) {
                case NOT_FOUND -> throw new NotFoundException(getErrorMessage(e));
                case UNPROCESSABLE_ENTITY -> throw new InvalidInputException(getErrorMessage(e));
                default -> {
                    log.warn("Got an unexpected HTTP error: {}, will rethrow it", e.getStatusCode());
                    log.warn("Error bod: {}", e.getResponseBodyAsString());
                    throw e;
                }
            }
        }
    }

    public void deleteRecommendations(long productId) {
        try {
            String url = recommendationServiceUrl + "?productId=" + productId;
            log.debug("Will call deleteRecommendation API on URL: {}", url);
            restTemplate.delete(url);
            log.debug("Delete the recommendation with id: {}", productId);
        } catch (HttpClientErrorException e) {
            switch (HttpStatus.resolve(e.getStatusCode().value())) {
                case NOT_FOUND -> throw new NotFoundException(getErrorMessage(e));
                case UNPROCESSABLE_ENTITY -> throw new InvalidInputException(getErrorMessage(e));
                default -> {
                    log.warn("Got an unexpected HTTP error: {}, will rethrow it", e.getStatusCode());
                    log.warn("Error bod: {}", e.getResponseBodyAsString());
                    throw e;
                }
            }
        }
    }

    public List<Review> getReviews(long productId) {
        try {
            String url = this.reviewServiceUrl + "?productId=" + productId;
            log.debug("Will call review service API on URL: {}", url);
            List<Review> reviews = restTemplate
                    .exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Review>>() {})
                    .getBody();
            log.debug("Found {} reviews for product id: {}", reviews.size(), productId);
            return reviews;
        } catch (Exception e) {
            log.debug("Got an exception while requesting reviews, return zero reviews: {}", e.getMessage());
            return List.of();
        }
    }

    public Review createReview(Review value) {
        try {
            String url = reviewServiceUrl;
            log.debug("Will call postReview API on URL: {}", url);
            Review review = restTemplate.postForObject(url, value, Review.class);
            log.debug("Created the review with id: {}", review.getReviewId());

            return review;
        } catch (HttpClientErrorException e) {
            if (HttpStatus.resolve(e.getStatusCode().value()) == HttpStatus.UNPROCESSABLE_ENTITY) {
                throw new InvalidInputException(getErrorMessage(e));
            }
            log.warn("Got an unexpected HTTP error: {}, will rethrow it", e.getStatusCode());
            log.warn("Error bod: {}", e.getResponseBodyAsString());
            throw e;
        }
    }

    public Review updateReview(Review value) {
        try {
            String url = reviewServiceUrl;
            log.debug("Will call putReview API on URL: {}", url);
            restTemplate.put(url, value, Review.class);
            log.debug("Update the review with id: {}", value.getReviewId());

            return value;
        } catch (HttpClientErrorException e) {
            switch (HttpStatus.resolve(e.getStatusCode().value())) {
                case NOT_FOUND -> throw new NotFoundException(getErrorMessage(e));
                case UNPROCESSABLE_ENTITY -> throw new InvalidInputException(getErrorMessage(e));
                default -> {
                    log.warn("Got an unexpected HTTP error: {}, will rethrow it", e.getStatusCode());
                    log.warn("Error bod: {}", e.getResponseBodyAsString());
                    throw e;
                }
            }
        }
    }

    public void deleteReviews(long productId) {
        try {
            String url = reviewServiceUrl + "?productId=" + productId;
            log.debug("Will call deleteReview API on URL: {}", url);
            restTemplate.delete(url);
            log.debug("Delete the review with id: {}", productId);
        } catch (HttpClientErrorException e) {
            switch (HttpStatus.resolve(e.getStatusCode().value())) {
                case NOT_FOUND -> throw new NotFoundException(getErrorMessage(e));
                case UNPROCESSABLE_ENTITY -> throw new InvalidInputException(getErrorMessage(e));
                default -> {
                    log.warn("Got an unexpected HTTP error: {}, will rethrow it", e.getStatusCode());
                    log.warn("Error bod: {}", e.getResponseBodyAsString());
                    throw e;
                }
            }
        }
    }

    private String getErrorMessage(HttpClientErrorException e) {
        try {
            return objectMapper.readValue(e.getResponseBodyAsString(), HttpErrorInfo.class).getMessage();
        } catch (JsonProcessingException jsonE) {
            return jsonE.getMessage();
        }
    }
}
