package com.unewej.mutual.api.core.composite.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceAddresses {
    private String composite;
    private String product;
    private String recommendation;
    private String review;
}
