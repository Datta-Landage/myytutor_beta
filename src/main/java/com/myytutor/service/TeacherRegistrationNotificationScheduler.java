package com.myytutor.service;

import com.myytutor.entity.Teacher;
import com.myytutor.repository.TeacherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Scheduled service to send daily reports of newly registered teachers to
 * consultant
 * Runs daily at 8:00 AM with teacher details in table format
 */
@Service
public class TeacherRegistrationNotificationScheduler {
    private static final Logger log = LoggerFactory.getLogger(TeacherRegistrationNotificationScheduler.class);

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private EmailService emailService;

    @Value("${mail.consult}")
    private String consultantEmail;

    /**
     * Scheduled task that runs in production every day at 22:00
     * Sends email with all teachers registered in the last 24 hours
     * Includes: Name, Phone, Email, Education, Experience, Registration Time, Rate,
     * Preferred Areas
     * 
     * NOTE: Cron set to run at 22:00 every day: "0 0 22 * * *"
     */
    @Scheduled(cron = "0 0 22 * * *") // Runs every day at 22:00 (production)
    @Transactional
    public void sendDailyTeacherRegistrationReport() {
        try {
            log.info("Starting daily teacher registration report generation...");

            // Get teachers registered in the last 24 hours
            LocalDateTime startTime = LocalDateTime.now().minusHours(24);
            List<Teacher> registeredTeachers = teacherRepository.findTeachersRegisteredSince(startTime);

            if (registeredTeachers.isEmpty()) {
                log.info("No new teachers registered in the last 24 hours. Skipping email.");
                return;
            }

            log.info("Found {} newly registered teacher(s) in the last 24 hours", registeredTeachers.size());

            // Send email to consultant
            sendTeacherRegistrationEmail(registeredTeachers);

        } catch (Exception e) {
            log.error("Error during daily teacher registration report generation", e);
        }
    }

    /**
     * Sends formatted email with teacher registration details in table format
     * 
     * @param teachers List of newly registered teachers
     */
    private void sendTeacherRegistrationEmail(List<Teacher> teachers) {
        try {
            String subject = String.format("📋 Daily Teacher Registration Report - %d New Teacher(s) (%s)",
                    teachers.size(),
                    DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDateTime.now()));

            sendHtmlEmail(consultantEmail, subject, teachers);

            log.info("Daily teacher registration report sent to: {}", consultantEmail);

        } catch (Exception e) {
            log.error("Error sending teacher registration email", e);
        }
    }

    /**
     * Sends HTML formatted email using Thymeleaf template
     */
    @Async
    private void sendHtmlEmail(String to, String subject, List<Teacher> teachers) {
        try {
            log.debug("Preparing teacher registration report email for: {}", to);

            // Calculate statistics
            long verifiedCount = teachers.stream()
                    .filter(t -> Boolean.TRUE.equals(t.getEmailVerified()))
                    .count();
            long experiencedCount = teachers.stream()
                    .filter(t -> t.getExperience() != null && t.getExperience() >= 2)
                    .count();

            // Create context for template
            org.thymeleaf.context.Context context = new org.thymeleaf.context.Context();
            // Prepare rows with formatted registration date, rate and preferred areas
            java.time.format.DateTimeFormatter rowDtf = java.time.format.DateTimeFormatter
                    .ofPattern("dd/MM/yyyy HH:mm:ss");

            java.util.List<java.util.Map<String, Object>> rows = teachers.stream().map(t -> {
                String created = "-";
                try {
                    if (t.getCreatedAt() != null) {
                        if (t.getCreatedAt().isAfter(LocalDateTime.now().plusDays(1))) {
                            created = "INVALID DATE";
                        } else {
                            created = rowDtf.format(t.getCreatedAt());
                        }
                    }
                } catch (Exception ex) {
                    created = String.valueOf(t.getCreatedAt());
                }

                String rateStr = t.getExpectedFeePerHour() != null ? String.valueOf(t.getExpectedFeePerHour()) : "-";

                String areas = "-";
                try {
                    if (t.getPreferredAreas() != null && !t.getPreferredAreas().isEmpty()) {
                        areas = t.getPreferredAreas().stream()
                                .map(pa -> pa.getArea())
                                .filter(java.util.Objects::nonNull)
                                .collect(java.util.stream.Collectors.joining(", "));
                    }
                } catch (Exception ex) {
                    areas = "-";
                }

                java.util.Map<String, Object> m = new java.util.HashMap<>();
                m.put("teacher", t);
                m.put("formattedCreatedAt", created);
                m.put("rate", rateStr);
                m.put("preferredAreas", areas);
                return m;
            }).collect(java.util.stream.Collectors.toList());

            context.setVariable("rows", rows);
            context.setVariable("verifiedCount", verifiedCount);
            context.setVariable("experiencedCount", experiencedCount);
            context.setVariable("reportDate", DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDateTime.now()));
            context.setVariable("reportDateTime",
                    DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(LocalDateTime.now()));

            // Process the template
            String emailContent = emailService.getTemplateEngine().process("email/teacher_registration_report",
                    context);

            // Create and send email using JavaMailSender
            jakarta.mail.internet.MimeMessage message = emailService.getJavaMailSender().createMimeMessage();
            org.springframework.mail.javamail.MimeMessageHelper helper = new org.springframework.mail.javamail.MimeMessageHelper(
                    message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(emailContent, true); // true = HTML content
            helper.setFrom(emailService.getFromEmail());

            emailService.getJavaMailSender().send(message);

            log.info("Teacher registration report email sent successfully to: {}", to);

        } catch (Exception e) {
            log.error("Error sending teacher registration HTML email", e);
        }
    }
}
