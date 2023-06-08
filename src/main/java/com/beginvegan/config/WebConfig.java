package com.beginvegan.config;

import com.beginvegan.filter.CookieAttributeFilter;
import lombok.Generated;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Generated
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:*", "http://beginvegan.kro.kr", "https://beginvegan.kro.kr")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE", "HEAD")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
    @Bean
    public javax.servlet.Filter cookieAttributeFilter() {
        return new CookieAttributeFilter();
    }
}
