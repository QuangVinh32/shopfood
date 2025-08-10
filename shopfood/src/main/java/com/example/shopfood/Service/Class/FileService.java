package com.example.shopfood.Service.Class;

import com.example.shopfood.Model.Entity.FileEntity;
import com.example.shopfood.Repository.FileRepository;
import com.example.shopfood.Service.IFileService;
import com.example.shopfood.Utils.FileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
@Service
public class FileService implements IFileService {
    @Autowired
    private FileRepository fileRepository;
    private final FileManager fileManager = new FileManager();

    public String uploadImage(MultipartFile image) throws IOException {
        String fileName = (new Date()).getTime() + "." + fileManager.getFormatFile(image.getOriginalFilename());
        String UPLOAD_DIR = "C:\\Users\\ADMIN\\IdeaProjects\\shopfood\\shopfood\\uploads\\images";
        String path = UPLOAD_DIR + "\\" + fileName;
        File directory = new File(UPLOAD_DIR);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        fileManager.createNewMultiPartFile(path, image);
        FileEntity fileEntity = new FileEntity();
        fileEntity.setName(fileName);
        fileEntity.setPath(path);
        fileRepository.save(fileEntity);
        return path;
    }
}
