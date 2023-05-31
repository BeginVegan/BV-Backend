package com.beginvegan.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MyServletContext implements WebMvcConfigurer {
    /**
     * CORS(교차 출처 리소스 공유) 관리
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowCredentials(true).allowedOriginPatterns("http://localhost:3000", "http://localhost:5173","http://beginvegan.kro.kr","https://beginvegan.kro.kr").allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}
