package com.sky.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * minio配置类
 */
@Configuration
@ConditionalOnProperty(name = "storage.type", havingValue = "minio")
public class MinioConfig {

    @Bean
    public MinioClient minioClient(
        @Value("${minio.endpoint}") String endpoint,
        @Value("${minio.access-key}") String ak,
        @Value("${minio.secret-key}") String sk
    ) {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(ak, sk)
                .build();
    }
}