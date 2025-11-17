package com.myytutor.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Validates all critical environment variables on application startup.
 * Prevents app from starting with missing configuration.
 */
@Component
public class EnvironmentValidator {
    private static final Logger log = LoggerFactory.getLogger(EnvironmentValidator.class);
    
    // Database (REQUIRED - no defaults)
    @Value("${DB_HOST:#{null}}")
    private String dbHost;
    
    @Value("${DB_PORT:#{null}}")
    private String dbPort;
    
    @Value("${DB_NAME:#{null}}")
    private String dbName;
    
    @Value("${DB_USER:#{null}}")
    private String dbUser;
    
    @Value("${DB_PASSWORD:#{null}}")
    private String dbPassword;
    
    // Mail (REQUIRED for OTP)
    @Value("${MAIL_HOST:#{null}}")
    private String mailHost;
    
    @Value("${MAIL_PORT:#{null}}")
    private String mailPort;
    
    @Value("${MAIL_USERNAME:#{null}}")
    private String mailUsername;
    
    @Value("${MAIL_PASSWORD:#{null}}")
    private String mailPassword;
    
    // Security (REQUIRED)
    @Value("${JWT_SECRET:#{null}}")
    private String jwtSecret;
    
    @Value("${JWT_TOKEN_VALIDITY:#{null}}")
    private String jwtValidity;
    
    @Value("${FRONTEND_SECRET:#{null}}")
    private String frontendSecret;
    
    // Google API (REQUIRED)
    @Value("${GOOGLE_API_KEY:#{null}}")
    private String googleApiKey;
    
    // WhatsApp (OPTIONAL - depends on provider)
    @Value("${whatsapp.provider:twilio}")
    private String whatsappProvider;
    
    @Value("${TWILIO_ACCOUNT_SID:#{null}}")
    private String twilioAccountSid;
    
    @Value("${TWILIO_AUTH_TOKEN:#{null}}")
    private String twilioAuthToken;
    
    @Value("${WHATSAPP_PHONE_NUMBER_ID:#{null}}")
    private String metaPhoneNumberId;
    
    @Value("${WHATSAPP_ACCESS_TOKEN:#{null}}")
    private String metaAccessToken;
    
    @Value("${WHATSAPP_COMMUNITY_ID:#{null}}")
    private String metaCommunityId;
    
    @EventListener(ApplicationReadyEvent.class)
    public void validateEnvironmentVariables() {
        log.info("=== ENVIRONMENT VALIDATION STARTING ===");
        
        List<String> missingVariables = new ArrayList<>();
        List<String> warnings = new ArrayList<>();
        
        // Critical database variables
        checkRequired("DB_HOST", dbHost, missingVariables);
        checkRequired("DB_PORT", dbPort, missingVariables);
        checkRequired("DB_NAME", dbName, missingVariables);
        checkRequired("DB_USER", dbUser, missingVariables);
        checkRequired("DB_PASSWORD", dbPassword, missingVariables);
        
        // Critical mail variables (required for OTP)
        checkRequired("MAIL_HOST", mailHost, missingVariables);
        checkRequired("MAIL_PORT", mailPort, missingVariables);
        checkRequired("MAIL_USERNAME", mailUsername, missingVariables);
        checkRequired("MAIL_PASSWORD", mailPassword, missingVariables);
        
        // Security variables
        checkRequired("JWT_SECRET", jwtSecret, missingVariables);
        checkRequired("JWT_TOKEN_VALIDITY", jwtValidity, missingVariables);
        checkRequired("FRONTEND_SECRET", frontendSecret, missingVariables);
        
        // Google API
        checkRequired("GOOGLE_API_KEY", googleApiKey, missingVariables);
        
        // WhatsApp provider-specific validation
        if ("twilio".equalsIgnoreCase(whatsappProvider)) {
            checkRequired("TWILIO_ACCOUNT_SID", twilioAccountSid, missingVariables);
            checkRequired("TWILIO_AUTH_TOKEN", twilioAuthToken, missingVariables);
            log.info("WhatsApp Provider: Twilio (configured)");
        } else if ("meta".equalsIgnoreCase(whatsappProvider)) {
            checkRequired("WHATSAPP_PHONE_NUMBER_ID", metaPhoneNumberId, missingVariables);
            checkRequired("WHATSAPP_ACCESS_TOKEN", metaAccessToken, missingVariables);
            checkRequired("WHATSAPP_COMMUNITY_ID", metaCommunityId, missingVariables);
            log.info("WhatsApp Provider: Meta (configured)");
        } else {
            warnings.add("Unknown whatsapp.provider: " + whatsappProvider + " (expected 'twilio' or 'meta')");
        }
        
        // Report results
        if (!missingVariables.isEmpty()) {
            log.error("=== CRITICAL: MISSING ENVIRONMENT VARIABLES ===");
            missingVariables.forEach(var -> log.error("  ❌ {}", var));
            log.error("==============================================");
            log.error("Application CANNOT function without these variables!");
            log.error("Set them in your environment or .env file before starting.");
            log.error("See CRITICAL_STARTUP_FIX.md for help.");
            throw new IllegalStateException(
                String.format("Missing %d critical environment variable(s): %s", 
                    missingVariables.size(), 
                    String.join(", ", missingVariables))
            );
        }
        
        if (!warnings.isEmpty()) {
            log.warn("=== CONFIGURATION WARNINGS ===");
            warnings.forEach(warning -> log.warn("  ⚠️  {}", warning));
            log.warn("==============================");
        }
        
        log.info("=== ✅ ENVIRONMENT VALIDATION PASSED ===");
        log.info("All critical environment variables are present");
        log.info("Database: {}:{}/{}", dbHost, dbPort, dbName);
        log.info("Mail Server: {}:{}", mailHost, mailPort);
        log.info("==========================================");
    }
    
    private void checkRequired(String name, String value, List<String> missingList) {
        if (value == null || value.trim().isEmpty()) {
            missingList.add(name);
        }
    }
}
