package com.unewej.microservices.recommendation.service.persistence;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "recommendations")
@Data
public class RecommendationEntity {
    @Id
    private String id;
    @Version
    private Integer version;
    @Indexed(unique = true)
    private int recommendationId;
    @Indexed()
    private int productId;
    private String author;
    private int rate;
    private String content;

    public RecommendationEntity(int recommendationId, int productId, String author, int rate, String content) {
        this.recommendationId = recommendationId;
        this.productId = productId;
        this.author = author;
        this.rate = rate;
        this.content = content;
    }
}
