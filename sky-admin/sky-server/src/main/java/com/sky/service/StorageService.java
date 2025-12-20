package com.sky.service;

import java.io.InputStream;

/**
 * 文件上传与下载
 */
public interface StorageService {

    String upload(InputStream inputStream, String objectName, String contentType);

    void delete(String objectName);
}
