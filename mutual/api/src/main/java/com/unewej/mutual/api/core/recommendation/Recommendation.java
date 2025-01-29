package com.unewej.mutual.api.core.recommendation;

import lombok.Data;

@Data
public class Recommendation {
    private final int recommendationId;
    private final int productId;
    private final String author;
    private final int rate;
    private final String content;
    private final String serviceAddress;

    public Recommendation() {
        this.recommendationId = 0;
        this.productId = 0;
        this.author = null;
        this.rate = 0;
        this.content = null;
        this.serviceAddress = null;
    }

    public Recommendation(int recommendationId, int productId, String author, int rate, String content, String serviceAddress) {
        this.recommendationId = recommendationId;
        this.productId = productId;
        this.author = author;
        this.rate = rate;
        this.content = content;
        this.serviceAddress = serviceAddress;
    }
}
