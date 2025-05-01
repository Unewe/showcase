package com.unewej.microservices.review.persistence;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reviews")
@Data
@NoArgsConstructor
public class ReviewEntity {
    @Id
    private String id;
    @Version
    private Integer version;
    @Indexed(unique = true)
    private Integer reviewId;
    @Indexed()
    private Long productId;
    private String author;
    private String subject;
    private String content;

    public ReviewEntity(Integer reviewId, Long productId, String author, String subject, String content) {
        this.reviewId = reviewId;
        this.productId = productId;
        this.author = author;
        this.subject = subject;
        this.content = content;
    }
}
