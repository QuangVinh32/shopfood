package com.example.shopfood.Model.DTO;

import lombok.Data;

import java.util.List;

@Data
public class ProductForAdmin {
    private String productName;
    private List<String> productImages;
    private String description;
    private Double price;
    private Integer discount;
    private Integer quantity;
    private Integer categoryId;
}
