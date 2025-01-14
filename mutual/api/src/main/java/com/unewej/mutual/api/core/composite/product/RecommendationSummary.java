package com.unewej.mutual.api.core.composite.product;

import lombok.Data;

@Data
public class RecommendationSummary {
    private final int id;
    private final String author;
    private final int rate;
    private final String content;

    public RecommendationSummary() {
        id = 0;
        author = null;
        rate = 0;
        content = null;
    }

    public RecommendationSummary(int id, String author, int rate, String content) {
        this.id = id;
        this.author = author;
        this.rate = rate;
        this.content = content;
    }
}
