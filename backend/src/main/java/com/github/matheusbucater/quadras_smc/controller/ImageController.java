package com.github.matheusbucater.quadras_smc.controller;

import com.github.matheusbucater.quadras_smc.dto.MessageDTO;
import com.github.matheusbucater.quadras_smc.service.ImageService;
import io.minio.MinioClient;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@RequestMapping("image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @SneakyThrows
    @PostMapping("/{bucketName}/upload")
    public ResponseEntity<MessageDTO> uploadImage(
            @RequestParam("file") MultipartFile file,
            @PathVariable String bucketName
    ) {

        String fileName = file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();
        String contentType = file.getContentType();

        String url = this.imageService.uploadFile(bucketName, fileName, inputStream, contentType);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new MessageDTO(url));
    }
}
