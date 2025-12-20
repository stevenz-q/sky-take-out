package com.sky.service.impl;

import com.aliyun.oss.OSS;
import com.sky.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * oss 存储服务
 */
@Slf4j
@Service
@ConditionalOnProperty(name = "storage.type", havingValue = "alioss")
public class OssStorageService implements StorageService {

    private final OSS ossClient;
    private final String bucketName;
    private final String domain; // 访问域名

    public OssStorageService(OSS ossClient, String bucketName, String domain) {
        this.ossClient = ossClient;
        this.bucketName = bucketName;
        this.domain = domain;
    }

    @Override
    public String upload(InputStream in, String objectName, String contentType) {
        try {
            ossClient.putObject(bucketName, objectName, in);
            return domain + "/" + objectName;
        } catch (Exception e) {
            log.error("OSS 上传失败", e);
            throw new RuntimeException("文件上传失败");
        }
    }

    @Override
    public void delete(String objectName) {
        ossClient.deleteObject(bucketName, objectName);
    }
}
