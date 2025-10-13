package com.example.shopfood.Service.Class;

import com.example.shopfood.Model.DTO.ProductForAdmin;
import com.example.shopfood.Model.DTO.ProductForUser;
import com.example.shopfood.Model.Entity.Category;
import com.example.shopfood.Model.Entity.Product;
import com.example.shopfood.Model.Entity.ProductImage;
import com.example.shopfood.Model.Entity.Review;
import com.example.shopfood.Model.Request.Product.CreateProduct;
import com.example.shopfood.Model.Request.Product.FilterProduct;
import com.example.shopfood.Model.Request.Product.UpdateProduct;
import com.example.shopfood.Repository.CategoryRepository;
import com.example.shopfood.Repository.ProductRepository;
import com.example.shopfood.Repository.ReviewRepository;
import com.example.shopfood.Service.IFileService;
import com.example.shopfood.Service.IProductService;
import com.example.shopfood.Specification.ProductSpecification;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private IFileService fileService;
    @Autowired
    private ReviewRepository reviewRepository;



    public Page<Product> getAllProductsPage(Pageable pageable, FilterProduct filterProduct) {
        Specification<Product> spec = ProductSpecification.buildSpec(filterProduct);
        return productRepository.findAll(spec, pageable);
    }

    public ProductForAdmin getProductByIdForAdmin(int id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            ProductForAdmin dto = new ProductForAdmin();
            dto.setProductName(product.getProductName());
//            dto.setProductImage(product.getProductImage());
            dto.setPrice(product.getPrice());
            dto.setDiscount(product.getDiscount());
            dto.setQuantity(product.getQuantity());
            dto.setDescription(product.getDescription());
            dto.setCategoryId(product.getCategory().getCategoryId());
            return dto;
        } else {
            throw new EntityNotFoundException("Product not found with id: " + id);
        }
    }

//    public void createProduct(CreateProduct createProduct) throws Exception {
//        Category category = categoryRepository.findById(createProduct.getCategoryId()).orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + createProduct.getCategoryId()));
////        String fileName = fileService.uploadImage(createProduct.getProductImage());
//        Product product = new Product();
////        product.setProductImage(fileName);
//        product.setProductName(createProduct.getProductName());
//        product.setPrice(createProduct.getPrice());
//        product.setDiscount(createProduct.getDiscount());
//        product.setQuantity(createProduct.getQuantity());
//        product.setDescription(createProduct.getDescription());
//        product.setCategory(category);
//        productRepository.save(product);
//    }

public void createProduct(CreateProduct createProduct) throws Exception {
    // Lấy category
    Category category = categoryRepository.findById(createProduct.getCategoryId())
            .orElseThrow(() -> new EntityNotFoundException(
                    "Category not found with id: " + createProduct.getCategoryId())
            );

    // Tạo product
    Product product = new Product();
    product.setProductName(createProduct.getProductName());
    product.setPrice(createProduct.getPrice());
    product.setDiscount(createProduct.getDiscount());
    product.setQuantity(createProduct.getQuantity());
    product.setDescription(createProduct.getDescription());
    product.setCategory(category);

    // Map danh sách ảnh từ DTO sang entity ProductImage
    if (createProduct.getProductImages() != null && !createProduct.getProductImages().isEmpty()) {
        List<ProductImage> images = createProduct.getProductImages().stream()
                .map(file -> {
                    try {
                        String path = fileService.uploadImage(file);
                        String fileName = new File(path).getName();
                        ProductImage img = new ProductImage();
                        img.setProductImageName(fileName);
                        img.setProductImagePath(path);
                        img.setProduct(product);
                        return img;
                    } catch (IOException e) {
                        throw new RuntimeException("Error saving image: " + file.getOriginalFilename(), e);
                    }
                })
                .toList();

        product.setProductImages(images);
    }

    // Lưu product (kèm danh sách ảnh nhờ cascade = ALL)
    productRepository.save(product);
}


    public void updateProduct(int productId, UpdateProduct updateProduct) throws Exception {
        Product existingProduct = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));
//        String fileName = fileService.uploadImage(updateProduct.getProductImage());
        if (updateProduct.getProductName() != null) {
            existingProduct.setProductName(updateProduct.getProductName());
        }

//        if (updateProduct.getProductImage() != null) {
//            existingProduct.setProductImage(fileName);
//        }

        if (updateProduct.getPrice() != null) {
            existingProduct.setPrice(updateProduct.getPrice());
        }

        if (updateProduct.getDiscount() != null) {
            existingProduct.setDiscount(updateProduct.getDiscount());
        }

        if (updateProduct.getDescription() != null) {
            existingProduct.setDescription(updateProduct.getDescription());
        }

        if (updateProduct.getCategoryId() != null) {
            Category category = categoryRepository.findById(updateProduct.getCategoryId()).orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + updateProduct.getCategoryId()));
            existingProduct.setCategory(category);
        }

        productRepository.save(existingProduct);
    }

    public void deleteProduct(int id) {
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
        productRepository.delete(existingProduct);
    }

    public boolean isProductNameExists(String productName) {
        return productRepository.existsByProductName(productName);
    }

    public ProductForUser getProductByIdForUser(int id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
        List<Review> reviews = reviewRepository.findByProductProductId(id);
        return new ProductForUser(product, reviews);
    }
}
