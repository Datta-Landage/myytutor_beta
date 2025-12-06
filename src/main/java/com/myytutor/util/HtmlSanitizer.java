package com.myytutor.util;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.springframework.stereotype.Component;

/**
 * Utility class for sanitizing HTML input to prevent XSS attacks.
 * Uses OWASP Java HTML Sanitizer library.
 */
@Component
public class HtmlSanitizer {

    /**
     * Policy that strips all HTML tags, allowing only plain text.
     * This is the most secure option for user-generated content.
     */
    private static final PolicyFactory POLICY = new HtmlPolicyBuilder()
            .toFactory();

    /**
     * Sanitizes a string by removing all HTML tags and potentially dangerous
     * content.
     * Returns null if input is null.
     * 
     * @param input The string to sanitize
     * @return Sanitized string with HTML removed, or null if input was null
     */
    public String sanitize(String input) {
        if (input == null) {
            return null;
        }
        return POLICY.sanitize(input).trim();
    }

    /**
     * Sanitizes a string and ensures it's not empty after sanitization.
     * 
     * @param input The string to sanitize
     * @return Sanitized non-empty string, or null if input was null or became empty
     */
    public String sanitizeNotEmpty(String input) {
        String sanitized = sanitize(input);
        return (sanitized != null && !sanitized.isEmpty()) ? sanitized : null;
    }
}
