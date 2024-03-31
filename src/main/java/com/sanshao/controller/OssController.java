package com.sanshao.controller;

import com.aliyun.oss.OSS;
import com.sanshao.common.lang.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * Oss图片上传
 */
@RestController
public class OssController {

    @Autowired
    private OSS ossClient;

    @PostMapping("/upload/img")
    public Result upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        int i = originalFilename.lastIndexOf(".");
        String png = originalFilename.substring(i);
        String bucketName = "liagnhua";
        String fileName = "itsource/" + UUID.randomUUID().toString().substring(0, 4) + png;
        ossClient.putObject(bucketName, fileName, file.getInputStream());
        String url = "https://" + bucketName + ".oss-cn-beijing.aliyuncs.com" + "/" + fileName;
        System.out.println("上传成功返回的：" + url);
        return Result.ok().data("fileUrl",url);
    }
}