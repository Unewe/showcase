package com.unewej.mutual.api.core.recommendation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recommendation {
    private Long productId;
    private Integer recommendationId;
    private String author;
    private int rate;
    private String content;
    private String serviceAddress;
}
