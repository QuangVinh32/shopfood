package com.example.shopfood.Controller;

import java.io.IOException;

import com.example.shopfood.Service.IFileService;
import com.example.shopfood.Utils.FileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin({"*"})
@RestController
@RequestMapping({"/files"})
@Validated
public class FileController {
    @Autowired
    private IFileService fileService;

    @PostMapping({"/image"})
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile image) throws IOException {
        FileManager fileManager = new FileManager();
        if (!fileManager.isTypeFileImage(image)) {
            return new ResponseEntity<>("File must be an image!", HttpStatus.UNPROCESSABLE_ENTITY);
        } else {
            String savedFileName = this.fileService.uploadImage(image);
            return new ResponseEntity<>(savedFileName, HttpStatus.OK);
        }
    }
}