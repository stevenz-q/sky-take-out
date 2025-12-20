package com.sky.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云oss配置类
 */
@Configuration
@ConditionalOnProperty(name = "storage.type", havingValue = "alioss")
public class OssConfig {

    @Bean
    public OSS ossClient(
        @Value("${alioss.endpoint}") String endpoint,
        @Value("${alioss.access-key-id}") String ak,
        @Value("${alioss.access-key-secret}") String sk
    ) {
        return new OSSClientBuilder().build(endpoint, ak, sk);
    }
}
