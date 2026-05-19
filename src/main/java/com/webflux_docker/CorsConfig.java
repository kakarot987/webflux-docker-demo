package com.webflux_docker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import java.util.List;

@Configuration
public class CorsConfig implements WebFluxConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Explicitly allow the dashboard origin(s) used by JetBrains preview and common local dev hosts.
        registry.addMapping("/**")
            .allowedOrigins(
                "http://localhost:63342",
                "http://127.0.0.1:63342",
                "http://localhost:8000",
                "http://localhost:3000",
                "http://localhost"
            )
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(false)
            .maxAge(3600);
    }

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowCredentials(false);
        // Be explicit about allowed origins to avoid wildcard/credentials conflicts and ensure
        // the expected Access-Control-Allow-Origin header is emitted for the dashboard origin.
        corsConfig.setAllowedOrigins(List.of(
            "http://localhost:63342",
            "http://127.0.0.1:63342",
            "http://localhost:8000",
            "http://localhost:3000",
            "http://localhost"
        ));
        corsConfig.addAllowedMethod("GET");
        corsConfig.addAllowedMethod("POST");
        corsConfig.addAllowedMethod("PUT");
        corsConfig.addAllowedMethod("DELETE");
        corsConfig.addAllowedMethod("OPTIONS");
        corsConfig.addAllowedHeader("*");
        corsConfig.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }
}


