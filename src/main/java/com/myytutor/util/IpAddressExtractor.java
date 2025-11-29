package com.myytutor.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

/**
 * Utility to extract real IP address from HTTP requests.
 * Handles proxy headers (X-Forwarded-For) commonly used in production.
 */
@Component
public class IpAddressExtractor {

    /**
     * Extract the real IP address from the request.
     * Checks proxy headers first, falls back to remote address.
     * 
     * @param request HTTP servlet request
     * @return IP address as string, or "unknown" if not available
     */
    public String getClientIpAddress(HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        }

        // Check X-Forwarded-For header (used by most proxies/load balancers)
        String ip = request.getHeader("X-Forwarded-For");
        if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
            // X-Forwarded-For can contain multiple IPs: "client, proxy1, proxy2"
            // First IP is the real client IP
            int commaIndex = ip.indexOf(',');
            if (commaIndex > 0) {
                ip = ip.substring(0, commaIndex).trim();
            }
            return ip.trim();
        }

        // Check other common proxy headers
        ip = request.getHeader("X-Real-IP");
        if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
            return ip.trim();
        }

        ip = request.getHeader("Proxy-Client-IP");
        if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
            return ip.trim();
        }

        ip = request.getHeader("WL-Proxy-Client-IP");
        if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
            return ip.trim();
        }

        // Fall back to remote address
        ip = request.getRemoteAddr();
        if (ip != null && !ip.isEmpty()) {
            return ip.trim();
        }

        return "unknown";
    }
}
