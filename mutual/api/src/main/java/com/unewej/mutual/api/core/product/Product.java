package com.unewej.mutual.api.core.product;

import lombok.Data;

import java.time.Instant;

@Data
public class Product {
    private long id;
    private String name;
    private int weight;
    private String serviceAddress;

    public Product() {
        this.id = 0;
        this.name = null;
        this.weight = 0;
        this.serviceAddress = null;
    }

    public Product(long id, String name, int weight, String serviceAddress) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.serviceAddress = serviceAddress;
    }
}
