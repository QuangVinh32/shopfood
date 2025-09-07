package com.example.shopfood.Model.Request.Product;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateProduct {
    private String productName;
//    private MultipartFile productImage;
    private String description;
    private Double price;
    private Integer discount;
    private Integer quantity;
    private Integer categoryId;
}
