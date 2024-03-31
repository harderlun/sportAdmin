package com.sanshao.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云Oss配置文件
 */
@Configuration
public class OssConfig {

    @Bean
    public OSS ossClient(){
        String endpoint = "oss-cn-beijing.aliyuncs.com";
        String accessKeyId = "LTAI5tEqz528c8zCQTo8HXXb";
        String accessKeySecret = "o5FNayjEOvdwHNCxkZCA7LbwPSKJxk";
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        return ossClient;
    }
}