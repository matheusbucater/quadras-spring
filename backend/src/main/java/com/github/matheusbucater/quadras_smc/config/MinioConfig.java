package com.github.matheusbucater.quadras_smc.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


 @Configuration
public class MinioConfig {

    @Value("${minio.url}")
    private String url;

    @Value("${minio.acceskey}")
    private String accessKey;

    @Value("${minio.secretkey}")
    private String secretKey;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(url)
                .credentials(accessKey, secretKey)
                .build();
    }
}
