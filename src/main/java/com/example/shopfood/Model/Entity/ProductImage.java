package com.example.shopfood.Model.Entity;
import jakarta.persistence.*;
@Entity
@Table(name = "product_image")
public class ProductImage {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Integer productImageId;
    @Column(
            name = "product_image"
    )
    private String productImage;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
