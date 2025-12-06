package com.myytutor.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Tracks email sending attempts to prevent email bombing attacks.
 * Rate limiting strategy:
 * - Max 3 OTP requests per email per hour
 * - Max 5 OTP requests per IP per hour
 * - Cooldown period: 1 minute between requests
 */
@Entity
@Table(name = "email_rate_limit", indexes = {
        @Index(name = "idx_email_created", columnList = "email,created_at"),
        @Index(name = "idx_ip_created", columnList = "ip_address,created_at")
})
public class EmailRateLimit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "email_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EmailType emailType;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public enum EmailType {
        OTP,
        REGISTRATION_SUCCESS,
        VERIFICATION_SUCCESS,
        WELCOME,
        LOGIN_NOTIFICATION
    }

    // Constructors
    public EmailRateLimit() {
        this.createdAt = LocalDateTime.now();
    }

    public EmailRateLimit(String email, String ipAddress, EmailType emailType) {
        this.email = email;
        this.ipAddress = ipAddress;
        this.emailType = emailType;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public EmailType getEmailType() {
        return emailType;
    }

    public void setEmailType(EmailType emailType) {
        this.emailType = emailType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
