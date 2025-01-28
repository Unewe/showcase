package com.unewej.microservices.review.service.persistence;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reviews")
@Data
public class ReviewEntity {
    @Id
    private String id;
    @Version
    private Integer version;
    @Indexed(unique = true)
    private final int reviewId;
    @Indexed()
    private final int productId;
    private final String author;
    private final String subject;
    private final String content;

    public ReviewEntity(int reviewId, int productId, String author, String subject, String content) {
        this.reviewId = reviewId;
        this.productId = productId;
        this.author = author;
        this.subject = subject;
        this.content = content;
    }
}
