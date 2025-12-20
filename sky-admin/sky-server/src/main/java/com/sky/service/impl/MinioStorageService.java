package com.sky.service.impl;

import com.sky.service.StorageService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * minio存储服务
 */
@Slf4j
@Service
@ConditionalOnProperty(name = "storage.type", havingValue = "minio")
public class MinioStorageService implements StorageService {

    private final MinioClient minioClient;
    private final String bucketName;
    private final String domain;

    public MinioStorageService(MinioClient minioClient,
                               @Value("${minio.bucket-name}") String bucketName,
                               @Value("${minio.domain}") String domain) {
        this.minioClient = minioClient;
        this.bucketName = bucketName;
        this.domain = domain;
    }

    @Override
    public String upload(InputStream in, String objectName, String contentType) {
        try {
            minioClient.putObject(
                PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(in, -1, 10 * 1024 * 1024)
                    .contentType(contentType)
                    .build()
            );
            return domain + "/" + objectName;
        } catch (Exception e) {
            log.error("MinIO 上传失败", e);
            throw new RuntimeException("文件上传失败");
        }
    }

    @Override
    public void delete(String objectName) {
        try {
            minioClient.removeObject(
                RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .build()
            );
        } catch (Exception e) {
            log.error("MinIO 删除失败", e);
        }
    }
}
