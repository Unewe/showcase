package com.unewej.mutual.api.core.product;

import lombok.Data;

@Data
public class Product {
    private final int id;
    private final String name;
    private final int weight;
    private final String serviceAddress;

    public Product() {
        this.id = 0;
        this.name = null;
        this.weight = 0;
        this.serviceAddress = null;
    }

    public Product(int id, String name, int weight, String serviceAddress) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.serviceAddress = serviceAddress;
    }
}
