package com.ab.bellingservices.model;

import lombok.Data;

@Data
public class Product {
    private Long id;
    private String name;
    private double price;
    private double quantity;
}