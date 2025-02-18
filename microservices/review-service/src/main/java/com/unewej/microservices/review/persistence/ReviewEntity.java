package com.unewej.microservices.review.persistence;

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
    private int reviewId;
    @Indexed()
    private int productId;
    private String author;
    private String subject;
    private String content;

    public ReviewEntity(int reviewId, int productId, String author, String subject, String content) {
        this.reviewId = reviewId;
        this.productId = productId;
        this.author = author;
        this.subject = subject;
        this.content = content;
    }
}
