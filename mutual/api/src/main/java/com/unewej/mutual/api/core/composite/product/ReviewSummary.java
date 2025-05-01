package com.unewej.mutual.api.core.composite.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewSummary {
    private String id;
    private Long productId;
    private Integer reviewId;
    private String author;
    private String subject;
    private String content;
}
