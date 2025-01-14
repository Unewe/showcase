package com.unewej.mutual.api.core.composite.product;

import lombok.Data;

@Data
public class ReviewSummary {
    private final int id;
    private final String author;
    private final String subject;
    private final String content;

    public ReviewSummary() {
        id = 0;
        author = null;
        subject = null;
        content = null;
    }

    public ReviewSummary(int id, String author, String subject, String content) {
        this.id = id;
        this.author = author;
        this.subject = subject;
        this.content = content;
    }
}
