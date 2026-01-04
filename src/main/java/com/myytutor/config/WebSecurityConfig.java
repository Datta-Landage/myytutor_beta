package com.myytutor.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private FrontendKeyFilter frontendKeyFilter;

    // CSV list or "*" for all
    @Value("${app.cors.allowed-origins:}")
    private String allowedOrigins;

    // strict | permissive
    @Value("${app.cors.mode:strict}")
    private String corsMode;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .addFilterBefore(frontendKeyFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(basic -> basic.disable())
                .formLogin(form -> form.disable());

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Allow common methods including preflight
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("Authorization", "Content-Type", "X-FRONTEND-KEY"));
        configuration.setMaxAge(3600L);

        // PERMISSIVE MODE => allow all origins (patterns), useful for dev/testing
        if ("permissive".equalsIgnoreCase(corsMode)
                || "*".equals(allowedOrigins != null ? allowedOrigins.trim() : "")) {
            // Use patterns so Spring will echo actual Origin header when credentials=true
            configuration.setAllowedOriginPatterns(List.of("*"));
            configuration.setAllowCredentials(true);
        } else {
            // STRICT MODE => parse CSV list
            if (allowedOrigins == null || allowedOrigins.trim().isEmpty()) {
                throw new IllegalStateException("CORS_ALLOWED_ORIGINS must be set when app.cors.mode=strict");
            }
            List<String> origins = Arrays.stream(allowedOrigins.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());

            // If any origin contains a wildcard (e.g. https://*.mahatutor.com) use patterns
            boolean anyPattern = origins.stream().anyMatch(s -> s.contains("*"));
            if (anyPattern) {
                configuration.setAllowedOriginPatterns(origins);
            } else {
                configuration.setAllowedOrigins(origins);
            }
            configuration.setAllowCredentials(true); // keep credentials enabled for strict mode
        }

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
