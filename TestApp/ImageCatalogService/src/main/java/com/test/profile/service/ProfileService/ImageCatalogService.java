package com.test.profile.service.ProfileService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class ImageCatalogService {

    public static void main(String[] args) {
        SpringApplication.run(ImageCatalogService.class, args);
    }
}
