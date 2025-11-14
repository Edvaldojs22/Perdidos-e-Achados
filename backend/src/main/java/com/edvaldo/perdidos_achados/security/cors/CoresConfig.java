package com.edvaldo.perdidos_achados.security.cors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CoresConfig {
    @Value("${APP_CORS_ALLOWED_ORIGINS}")
    private String[] allowedOrigins;
    @Bean
    public WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry){
                registry.addMapping("/**")
                        .allowedOrigins(allowedOrigins)
                        .allowedMethods("GET","POST","DELETE","OPTIONS","PUT")
                        .allowedHeaders("*")
                        .allowCredentials(true);

            }
        };
    }
    
}
