package com.example.shopfood.Model.DTO;

import lombok.Data;

@Data
public class ProductForAdmin {
    private String productName;
//    private String productImage;
    private String description;
    private Double price;
    private Integer discount;
    private Integer quantity;
    private Integer categoryId;
}
