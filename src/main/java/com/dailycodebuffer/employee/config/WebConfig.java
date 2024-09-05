package com.dailycodebuffer.employee.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        System.out.println("Inside addCorsMappings()....");
        registry.addMapping("/api/**")
                .allowedOrigins("http://http://3.131.95.185:3000")
                .allowedOrigins("http://http://localhost:3000")
                .allowedMethods("POST","GET","PUT", "DELETE")
                // .allowedHeaders("header1", "header2", "header3")
                // .exposedHeaders("header1", "header2")
                // .allowCredentials(false)
                .maxAge(3600);
    }
}