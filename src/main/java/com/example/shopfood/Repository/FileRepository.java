package com.example.shopfood.Repository;

import com.example.shopfood.Model.Entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
}
