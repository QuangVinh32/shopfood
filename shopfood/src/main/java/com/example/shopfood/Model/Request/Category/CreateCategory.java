package com.example.shopfood.Model.Request.Category;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateCategory {
    private String categoryName;
    private MultipartFile categoryImage;
}
