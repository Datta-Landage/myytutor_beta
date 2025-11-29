package com.myytutor.service;

import com.myytutor.entity.EmailRateLimit;
import com.myytutor.entity.EmailRateLimit.EmailType;
import com.myytutor.repository.EmailRateLimitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

/**
 * Service to prevent email bombing attacks with rate limiting.
 * 
 * Rate Limits:
 * - OTP Emails: 3 per email per hour, 5 per IP per hour, 1 minute cooldown
 * - Other Emails: 10 per email per hour, 20 per IP per hour
 */
@Service
public class EmailRateLimitService {
    private static final Logger log = LoggerFactory.getLogger(EmailRateLimitService.class);

    @Autowired
    private EmailRateLimitRepository rateLimitRepository;

    // Rate limit configurations
    private static final int OTP_MAX_PER_EMAIL_PER_HOUR = 3;
    private static final int OTP_MAX_PER_IP_PER_HOUR = 5;
    private static final int OTP_COOLDOWN_SECONDS = 60; // 1 minute

    private static final int GENERAL_MAX_PER_EMAIL_PER_HOUR = 10;
    private static final int GENERAL_MAX_PER_IP_PER_HOUR = 20;

    /**
     * Check if email sending is allowed and record the attempt.
     * 
     * @param email     Recipient email address
     * @param ipAddress Sender's IP address (optional but recommended)
     * @param emailType Type of email being sent
     * @throws IllegalStateException if rate limit exceeded
     */
    @Transactional(timeout = 30)
    public void checkAndRecordEmailAttempt(String email, String ipAddress, EmailType emailType) {
        LocalDateTime now = LocalDateTime.now();

        // Special strict limits for OTP emails
        if (emailType == EmailType.OTP) {
            checkOtpRateLimits(email, ipAddress, now);
        } else {
            checkGeneralRateLimits(email, ipAddress, now);
        }

        // Record this attempt
        EmailRateLimit record = new EmailRateLimit(email, ipAddress, emailType);
        rateLimitRepository.save(record);

        log.debug("Email attempt recorded: {} to {} from IP {}", emailType, email, ipAddress);
    }

    private void checkOtpRateLimits(String email, String ipAddress, LocalDateTime now) {
        // Check cooldown period (1 minute since last OTP)
        EmailRateLimit lastAttempt = rateLimitRepository.findMostRecentByEmailAndType(email, EmailType.OTP);
        if (lastAttempt != null) {
            long secondsSinceLastAttempt = java.time.Duration.between(lastAttempt.getCreatedAt(), now).getSeconds();
            if (secondsSinceLastAttempt < OTP_COOLDOWN_SECONDS) {
                long remainingSeconds = OTP_COOLDOWN_SECONDS - secondsSinceLastAttempt;
                throw new IllegalStateException(
                        String.format("Please wait %d seconds before requesting another OTP", remainingSeconds));
            }
        }

        // Check email-based limit (3 OTPs per hour)
        LocalDateTime oneHourAgo = now.minusHours(1);
        long emailCount = rateLimitRepository.countByEmailAndTypeAndCreatedAtAfter(email, EmailType.OTP, oneHourAgo);

        if (emailCount >= OTP_MAX_PER_EMAIL_PER_HOUR) {
            log.warn("OTP rate limit exceeded for email: {} ({} attempts in last hour)", email, emailCount);
            throw new IllegalStateException(
                    String.format("Too many OTP requests. Maximum %d OTPs per hour. Please try again later.",
                            OTP_MAX_PER_EMAIL_PER_HOUR));
        }

        // Check IP-based limit (5 OTPs per hour per IP) - if IP provided
        if (ipAddress != null && !ipAddress.isEmpty()) {
            long ipCount = rateLimitRepository.countByIpAddressAndTypeAndCreatedAtAfter(ipAddress, EmailType.OTP,
                    oneHourAgo);

            if (ipCount >= OTP_MAX_PER_IP_PER_HOUR) {
                log.warn("OTP rate limit exceeded for IP: {} ({} attempts in last hour)", ipAddress, ipCount);
                throw new IllegalStateException(
                        "Too many OTP requests from your network. Please try again later.");
            }
        }
    }

    private void checkGeneralRateLimits(String email, String ipAddress, LocalDateTime now) {
        LocalDateTime oneHourAgo = now.minusHours(1);

        // Check email-based limit
        long emailCount = rateLimitRepository.countByEmailAndTypeAndCreatedAtAfter(email, EmailType.OTP, oneHourAgo);
        if (emailCount >= GENERAL_MAX_PER_EMAIL_PER_HOUR) {
            log.warn("Email rate limit exceeded for email: {} ({} attempts in last hour)", email, emailCount);
            throw new IllegalStateException("Too many email requests. Please try again later.");
        }

        // Check IP-based limit (if IP provided)
        if (ipAddress != null && !ipAddress.isEmpty()) {
            long ipCount = rateLimitRepository.countByIpAddressAndTypeAndCreatedAtAfter(ipAddress, EmailType.OTP,
                    oneHourAgo);
            if (ipCount >= GENERAL_MAX_PER_IP_PER_HOUR) {
                log.warn("Email rate limit exceeded for IP: {} ({} attempts in last hour)", ipAddress, ipCount);
                throw new IllegalStateException("Too many email requests from your network. Please try again later.");
            }
        }
    }

    /**
     * Cleanup old rate limit records (runs daily at 3 AM).
     * Keeps last 7 days of data for analysis.
     */
    @Scheduled(cron = "0 0 3 * * ?")
    @Transactional(timeout = 30)
    public void cleanupOldRecords() {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        rateLimitRepository.deleteOldRecords(sevenDaysAgo);
        log.info("Cleaned up email rate limit records older than 7 days");
    }

    /**
     * Get remaining OTP attempts for an email address.
     * Useful for displaying to users.
     */
    public int getRemainingOtpAttempts(String email) {
        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);
        long count = rateLimitRepository.countByEmailAndTypeAndCreatedAtAfter(email, EmailType.OTP, oneHourAgo);
        return Math.max(0, OTP_MAX_PER_EMAIL_PER_HOUR - (int) count);
    }
}
