package com.unewej.mutual.api.core.composite.product;

import lombok.Data;

@Data
public class ServiceAddresses {
    private final String composite;
    private final String product;
    private final String recommendation;
    private final String review;

    public ServiceAddresses() {
        composite = null;
        product = null;
        recommendation = null;
        review = null;
    }

    public ServiceAddresses(String composite, String product, String recommendation, String review) {
        this.composite = composite;
        this.product = product;
        this.recommendation = recommendation;
        this.review = review;
    }
}
