package com.bezkoder.springjwt.Config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("http://localhost:4200") // Autoriser les requêtes depuis Angular (localhost:4200)
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Autoriser les méthodes HTTP spécifiées
                .allowedHeaders("Content-Type", "Authorization") // Autoriser les en-têtes spécifiés
                .allowCredentials(true); // Autoriser les cookies avec les requêtes
    }
}


