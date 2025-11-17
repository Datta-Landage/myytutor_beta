package com.myytutor.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class FrontendKeyFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(FrontendKeyFilter.class);

    private static final List<String> EXCLUDED_PATHS = Arrays.asList(
            "/error",
            "/actuator/health",
            "/actuator/info");

    @Value("${frontend.secret}")
    private String frontendSecret;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        String origin = request.getHeader("Origin");

        // Log request details for debugging
        logger.debug("Request: {} {} from origin: {}", request.getMethod(), path, origin);

        // Skip validation for excluded paths and OPTIONS requests
        if (shouldSkipFilter(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String frontendKey = request.getHeader("X-FRONTEND-KEY");

        // Validate frontend key
        if (frontendKey == null || !frontendKey.equals(frontendSecret)) {
            logger.warn("Invalid or missing X-FRONTEND-KEY header for path: {}", path);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid or missing X-FRONTEND-KEY header");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean shouldSkipFilter(HttpServletRequest request) {
        String path = request.getRequestURI();

        // Skip OPTIONS requests (CORS preflight)
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }

        // Skip excluded paths
        return EXCLUDED_PATHS.stream().anyMatch(path::startsWith);
    }
}