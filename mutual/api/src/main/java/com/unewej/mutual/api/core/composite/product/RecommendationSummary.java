package com.unewej.mutual.api.core.composite.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationSummary {
    private Long productId;
    private Integer recommendationId;
    private String author = null;
    private int rate = 0;
    private String content = null;
}
