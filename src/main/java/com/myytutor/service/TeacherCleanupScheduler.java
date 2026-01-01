package com.myytutor.service;

import com.myytutor.repository.TeacherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Scheduled service to clean up unverified teacher accounts
 * Runs daily at 2 AM to remove accounts that:
 * 1. Have not verified their email
 * 2. OTP has expired (more than 24 hours old)
 */
@Service
public class TeacherCleanupScheduler {
    private static final Logger log = LoggerFactory.getLogger(TeacherCleanupScheduler.class);

    @Autowired
    private TeacherRepository teacherRepository;

    /**
     * Scheduled task that runs daily at 2:00 AM
     * Removes teacher accounts with unverified emails where OTP is older than 24
     * hours
     * 
     * This allows users to:
     * - Request new OTP if they forgot to verify
     * - Re-register with the same email after expiry
     * - Keep the database clean from incomplete registrations
     */
    @Scheduled(cron = "0 0 2 * * *") // Runs at 2:00 AM every day
    @Transactional
    public void cleanupUnverifiedAccounts() {
        try {
            log.info("Starting cleanup of unverified teacher accounts...");

            // Calculate cutoff time: 24 hours ago
            LocalDateTime cutoffTime = LocalDateTime.now().minusHours(24);

            // Find and delete teachers who:
            // 1. Email is not verified (emailVerified = false)
            // 2. OTP was generated more than 24 hours ago
            int deletedCount = teacherRepository.deleteByEmailVerifiedFalseAndEmailOtpGeneratedAtBefore(cutoffTime);

            if (deletedCount > 0) {
                log.info("Cleanup completed: {} unverified teacher account(s) removed", deletedCount);
            } else {
                log.info("Cleanup completed: No unverified accounts found to remove");
            }

        } catch (Exception e) {
            log.error("Error during cleanup of unverified accounts", e);
        }
    }

    /**
     * Additional scheduled task that runs every 6 hours
     * Removes expired OTPs from verified accounts to save database space
     */
    @Scheduled(cron = "0 0 */6 * * *") // Runs every 6 hours
    @Transactional
    public void cleanupExpiredOtps() {
        try {
            log.info("Starting cleanup of expired OTPs...");

            // Calculate cutoff time: 1 hour ago (OTP is valid for 5 minutes, so 1 hour is
            // safe)
            LocalDateTime cutoffTime = LocalDateTime.now().minusHours(1);

            // Clear OTP fields for accounts where:
            // 1. Email is already verified
            // 2. OTP was generated more than 1 hour ago
            int updatedCount = teacherRepository.clearExpiredOtps(cutoffTime);

            if (updatedCount > 0) {
                log.info("OTP cleanup completed: {} expired OTP(s) cleared", updatedCount);
            } else {
                log.info("OTP cleanup completed: No expired OTPs found to clear");
            }

        } catch (Exception e) {
            log.error("Error during cleanup of expired OTPs", e);
        }
    }
}
