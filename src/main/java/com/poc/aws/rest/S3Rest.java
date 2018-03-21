package com.poc.aws.rest;

import com.poc.aws.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableAutoConfiguration
public class S3Rest {

    @Autowired
    public S3Service s3Service;

    @RequestMapping(path = "/hi")
    public String getTestData() {
        return "Welcome to Spring Boot Application";
    }

    @PostMapping("/upload/{fileName}/{fileExt}")
    public String uploadImage(@PathVariable(name = "fileName") String fileName, @PathVariable(name = "fileExt") String fileExt, @RequestBody String base64Image) {
        return s3Service.uploadToS3(fileName, fileExt, base64Image);
    }
}
