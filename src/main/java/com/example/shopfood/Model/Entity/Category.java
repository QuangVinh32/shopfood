package com.example.shopfood.Model.Entity;
import lombok.Data;

import jakarta.persistence.*;

@Data
@Entity
@Table(
        name = "categories"
)
public class Category {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Integer categoryId;
    @Column(
            name = "category_name"
    )
    private String categoryName;
    @Column(
            name = "category_image"
    )
    private String categoryImage;
}
