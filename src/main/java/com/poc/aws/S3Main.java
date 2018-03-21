package com.poc.aws;

import com.poc.aws.config.ApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({ApplicationConfig.class})
@SpringBootApplication
public class S3Main {

    public static void main(String[] args) {
        System.out.println("iii my first project....");
        SpringApplication.run(S3Main.class, args);
    }
}
