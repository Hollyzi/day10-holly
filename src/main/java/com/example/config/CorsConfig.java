package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
//    @Override
//    public void addCorsMapping(CorsRegistry registry){
//        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:3000")
//                .allowedMethods("GET","POST","PUT","DELETE")
//                .allowedHeaders("Content-Type","Authorization")
//                .exposedHeaders("Authorization")
//                .allowCredentials(true)
//                .maxAge(3600);
//    }
}
