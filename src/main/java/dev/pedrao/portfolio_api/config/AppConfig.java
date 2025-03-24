package dev.pedrao.portfolio_api.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class AppConfig {

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create("mongodb+srv://admin:0420@portfolio-api.1bejc.mongodb.net/?retryWrites=true&w=majority&appName=portfolio-api");
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), "portfolio");
    }
}