package com.myytutor.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import jakarta.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
public class EmailService {
    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${mail.from}")
    private String fromEmail;

    public void sendOtp(String to, String otp) {
        try {
            // Create context for template
            Context context = new Context();
            context.setVariable("subject", "🔐 Verify Your Email - MyyTutor Registration");
            context.setVariable("otp", otp);

            // Process the OTP template with layout
            String otpContent = templateEngine.process("email/otp_email", context);
            context.setVariable("body", otpContent);
            String finalContent = templateEngine.process("email/layout", context);

            // Create and send email
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject("🔐 Verify Your Email - MyyTutor Registration");
            helper.setText(finalContent, true);

            mailSender.send(message);
            log.info("OTP email sent successfully to: {}", to);
        } catch (Exception e) {
            log.error("Failed to send OTP email to: {}", to, e);
            throw new RuntimeException("Failed to send OTP email", e);
        }
    }

    public void sendRegistrationSuccess(String to, String name, com.myytutor.entity.Teacher teacher) {
        try {
            Context context = new Context();
            context.setVariable("subject", "🎉 Registration Successful - Welcome to MyyTutor!");
            context.setVariable("name", name);
            context.setVariable("email", to);
            context.setVariable("registrationDate", java.time.LocalDateTime.now().format(
                java.time.format.DateTimeFormatter.ofPattern("MMMM dd, yyyy 'at' hh:mm a")
            ));
            context.setVariable("teacher", teacher);

            // Process registration success template with layout
            String successContent = templateEngine.process("email/registration_success", context);
            context.setVariable("body", successContent);
            String finalContent = templateEngine.process("email/layout", context);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject("🎉 Registration Successful - Welcome to MyyTutor!");
            helper.setText(finalContent, true);

            mailSender.send(message);
            log.info("Registration success email sent to: {}", to);
        } catch (Exception e) {
            log.error("Failed to send registration success email to: {}", to, e);
            throw new RuntimeException("Failed to send registration success email", e);
        }
    }

    public void sendVerificationSuccess(String to, String name) {
        try {
            Context context = new Context();
            context.setVariable("subject", "✅ Email Verified Successfully - MyyTutor");
            context.setVariable("name", name);

            String verificationContent = templateEngine.process("email/verification_success", context);
            context.setVariable("body", verificationContent);
            String finalContent = templateEngine.process("email/layout", context);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject("✅ Email Verified Successfully - MyyTutor");
            helper.setText(finalContent, true);

            mailSender.send(message);
            log.info("Verification success email sent to: {}", to);
        } catch (Exception e) {
            log.error("Failed to send verification success email to: {}", to, e);
            throw new RuntimeException("Failed to send verification success email", e);
        }
    }

    public void sendWelcomeEmail(String to, String name) {
        try {
            Context context = new Context();
            context.setVariable("subject", "Welcome to Our Community!");
            context.setVariable("name", name);

            String welcomeContent = templateEngine.process("email/welcome_email", context);
            context.setVariable("body", welcomeContent);
            String finalContent = templateEngine.process("email/layout", context);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject("Welcome to Our Community!");
            helper.setText(finalContent, true);

            mailSender.send(message);
            log.info("Welcome email sent to: {}", to);
        } catch (Exception e) {
            log.error("Failed to send welcome email to: {}", to, e);
            throw new RuntimeException("Failed to send welcome email", e);
        }
    }
}