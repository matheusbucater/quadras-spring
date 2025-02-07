package com.github.matheusbucater.quadras_smc.service;

import io.minio.*;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

@Service
public class ImageService {

    @Value("${minio.url}")
    private String minioUrl;

    @Autowired
    private MinioClient minioClient;

    public String uploadFile(String bucketName, String fileName, InputStream inputStream, String contentType) {

        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                throw new RuntimeException("Bucket não existe");
            }

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .stream(inputStream, inputStream.available(), -1)
                            .contentType(contentType)
                            .build()
            );

            return minioUrl + "/" + bucketName + "/" + fileName;
        } catch (Exception e) {
            throw new RuntimeException("Error ocurred: " + e.getMessage());
        }
    }
}
