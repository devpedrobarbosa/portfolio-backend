package dev.pedrao.portfolio_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("https://www.pedrao.tech", "http://localhost:3000")
                        .allowedMethods("POST", "PUT", "DELETE", "OPTIONS")
                        .allowCredentials(true);

                registry.addMapping("/**")
                        .allowedMethods("GET")
                        .allowedOrigins("*");
            }
        };
    }
}