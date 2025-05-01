package com.unewej.mutual.api.core.review;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    private String id;
    private Long productId;
    private Integer reviewId;
    private String author;
    private String subject;
    private String content;
    private String serviceAddress;
}
