package com.unewej.mutual.api.core.review;

import lombok.Data;

@Data
public class Review {
    private final int reviewId;
    private final int productId;
    private final String author;
    private final String subject;
    private final String content;
    private final String serviceAddress;

    public Review() {
        this.reviewId = 0;
        this.productId = 0;
        this.author = null;
        this.subject = null;
        this.content = null;
        this.serviceAddress = null;
    }

    public Review(int id, int productId, String author, String subject, String content, String serviceAddress) {
        this.reviewId = id;
        this.productId = productId;
        this.author = author;
        this.subject = subject;
        this.content = content;
        this.serviceAddress = serviceAddress;
    }
}
