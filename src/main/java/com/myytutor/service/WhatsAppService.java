package com.myytutor.service;

import com.myytutor.config.WhatsAppConfig;
import com.myytutor.dto.WhatsAppMessageRequest;
import com.myytutor.entity.Inquiry;
import com.myytutor.entity.Teacher;
import com.myytutor.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * WhatsApp Business Cloud API Integration Service
 * Handles all WhatsApp messaging operations
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WhatsAppService {

    private final WhatsAppConfig whatsAppConfig;
    private final RestTemplate restTemplate;
    private final TeacherRepository teacherRepository;

    /**
     * 1. Send auto-reply to customer when inquiry is received
     * Uses approved template: enquiry_confirmation
     */
    public void sendInquiryConfirmation(String customerPhone, String customerName, String inquiryId) {
        try {
            String formattedPhone = formatPhoneNumber(customerPhone);
            
            // Use approved Meta template: enquiry_confirmation
            // Template: Hello {{1}}, We have received Your enquiry ID: *{{2}}*. and our team will connect with you within 30 minutes with the best suitable tutor.
            WhatsAppMessageRequest request = WhatsAppMessageRequest.builder()
                    .messagingProduct("whatsapp")
                    .to(formattedPhone)
                    .type("template")
                    .template(WhatsAppMessageRequest.TemplateMessage.builder()
                            .name("enquiry_confirmation")
                            .language(WhatsAppMessageRequest.TemplateMessage.Language.builder()
                                    .code("en")
                                    .build())
                            .components(Arrays.asList(
                                    WhatsAppMessageRequest.TemplateMessage.Component.builder()
                                            .type("body")
                                            .parameters(Arrays.asList(
                                                    WhatsAppMessageRequest.TemplateMessage.Component.Parameter.builder()
                                                            .type("text")
                                                            .text(customerName)
                                                            .build(),
                                                    WhatsAppMessageRequest.TemplateMessage.Component.Parameter.builder()
                                                            .type("text")
                                                            .text(inquiryId)
                                                            .build()
                                            ))
                                            .build()
                            ))
                            .build())
                    .build();

            sendMessage(request);
            log.info("✅ Sent inquiry confirmation to customer: {} using template", customerPhone);
        } catch (Exception e) {
            log.error("❌ Failed to send inquiry confirmation to {}: {}", customerPhone, e.getMessage());
        }
    }

    /**
     * 2. Send inquiry summary to community
     * Uses approved template: enquiry_summary_community
     * Template: A new enquiry is available for review. Class: {{1}} Subjects: {{2}} Board:{{5}} Location: {{3}} Preferred Time: {{4}} Preferred Date: {{6}}
     */
    public void broadcastInquiryToCommunity(Inquiry inquiry) {
        try {
            String adminPhone = formatPhoneNumber(whatsAppConfig.getAdminPhone());
            
            // Use message field for subjects (contains all subjects as string)
            String subjects = inquiry.getMessage() != null && !inquiry.getMessage().isEmpty()
                    ? inquiry.getMessage()
                    : "Not specified";

            // Convert minutes to readable time format
            String preferredTime = "Flexible";
            if (inquiry.getSelectedStartTime() != null) {
                preferredTime = formatTimeFromMinutes(inquiry.getSelectedStartTime());
                if (inquiry.getSelectedEndTime() != null) {
                    preferredTime += " - " + formatTimeFromMinutes(inquiry.getSelectedEndTime());
                }
            }
            
            String preferredDate = inquiry.getSelectedStartDate() != null 
                    ? inquiry.getSelectedStartDate().toString() 
                    : "Flexible";

            // Use approved template: enquiry_summary_community
            WhatsAppMessageRequest request = WhatsAppMessageRequest.builder()
                    .messagingProduct("whatsapp")
                    .to(adminPhone)
                    .type("template")
                    .template(WhatsAppMessageRequest.TemplateMessage.builder()
                            .name("enquiry_summary_community")
                            .language(WhatsAppMessageRequest.TemplateMessage.Language.builder()
                                    .code("en")
                                    .build())
                            .components(Arrays.asList(
                                    WhatsAppMessageRequest.TemplateMessage.Component.builder()
                                            .type("body")
                                            .parameters(Arrays.asList(
                                                    WhatsAppMessageRequest.TemplateMessage.Component.Parameter.builder()
                                                            .type("text")
                                                            .text(inquiry.getClassStandard())
                                                            .build(),
                                                    WhatsAppMessageRequest.TemplateMessage.Component.Parameter.builder()
                                                            .type("text")
                                                            .text(subjects)
                                                            .build(),
                                                    WhatsAppMessageRequest.TemplateMessage.Component.Parameter.builder()
                                                            .type("text")
                                                            .text(inquiry.getAddress())
                                                            .build(),
                                                    WhatsAppMessageRequest.TemplateMessage.Component.Parameter.builder()
                                                            .type("text")
                                                            .text(preferredTime)
                                                            .build(),
                                                    WhatsAppMessageRequest.TemplateMessage.Component.Parameter.builder()
                                                            .type("text")
                                                            .text(inquiry.getBoard() != null ? inquiry.getBoard() : "Not specified")
                                                            .build(),
                                                    WhatsAppMessageRequest.TemplateMessage.Component.Parameter.builder()
                                                            .type("text")
                                                            .text(preferredDate)
                                                            .build()
                                            ))
                                            .build()
                            ))
                            .build())
                    .build();

            sendMessage(request);
            log.info("✅ Sent community inquiry summary using template for inquiry #{}", inquiry.getId());
            
        } catch (Exception e) {
            log.error("❌ Failed to send community message: {}", e.getMessage());
        }
    }

    /**
     * 3. Send full inquiry details to admin
     */
    public void sendFullInquiryToAdmin(Inquiry inquiry) {
        try {
            String adminPhone = formatPhoneNumber(whatsAppConfig.getAdminPhone());
            
            // Use message field for subjects (contains all subjects as string)
            String subjects = inquiry.getMessage() != null && !inquiry.getMessage().isEmpty()
                    ? inquiry.getMessage()
                    : "Not specified";

            // Use approved template: enquiry_details_admin
            WhatsAppMessageRequest request = WhatsAppMessageRequest.builder()
                    .messagingProduct("whatsapp")
                    .to(adminPhone)
                    .type("template")
                    .template(WhatsAppMessageRequest.TemplateMessage.builder()
                            .name("enquiry_details_admin")
                            .language(WhatsAppMessageRequest.TemplateMessage.Language.builder()
                                    .code("en")
                                    .build())
                            .components(Arrays.asList(
                                    WhatsAppMessageRequest.TemplateMessage.Component.builder()
                                            .type("body")
                                            .parameters(Arrays.asList(
                                                    WhatsAppMessageRequest.TemplateMessage.Component.Parameter.builder()
                                                            .type("text")
                                                            .text(inquiry.getName())
                                                            .build(),
                                                    WhatsAppMessageRequest.TemplateMessage.Component.Parameter.builder()
                                                            .type("text")
                                                            .text(inquiry.getPhone())
                                                            .build(),
                                                    WhatsAppMessageRequest.TemplateMessage.Component.Parameter.builder()
                                                            .type("text")
                                                            .text(inquiry.getClassStandard())
                                                            .build(),
                                                    WhatsAppMessageRequest.TemplateMessage.Component.Parameter.builder()
                                                            .type("text")
                                                            .text(subjects)
                                                            .build(),
                                                    WhatsAppMessageRequest.TemplateMessage.Component.Parameter.builder()
                                                            .type("text")
                                                            .text(inquiry.getAddress())
                                                            .build(),
                                                    WhatsAppMessageRequest.TemplateMessage.Component.Parameter.builder()
                                                            .type("text")
                                                            .text(String.valueOf(inquiry.getId()))
                                                            .build(),
                                                    WhatsAppMessageRequest.TemplateMessage.Component.Parameter.builder()
                                                            .type("text")
                                                            .text(inquiry.getBoard() != null ? inquiry.getBoard() : "Not specified")
                                                            .build()
                                            ))
                                            .build()
                            ))
                            .build())
                    .build();

            sendMessage(request);
            log.info("✅ Sent full inquiry details to admin using template for inquiry #{}", inquiry.getId());
        } catch (Exception e) {
            log.error("Failed to send inquiry to admin: {}", e.getMessage());
        }
    }

    /**
     * 4. Add teacher to WhatsApp community after successful registration
     * Sends WhatsApp group invite link
     */
    public void addTeacherToCommunity(Teacher teacher) {
        try {
            // Use WhatsApp number if available, fallback to phone number
            String phone = teacher.getWhatsappNumber() != null && !teacher.getWhatsappNumber().isEmpty() 
                    ? teacher.getWhatsappNumber() 
                    : teacher.getPhoneNumber();
            String teacherPhone = formatPhoneNumber(phone);
            
            log.debug("Formatted phone: {}", teacherPhone);
            log.debug("Using teacher_registration_success template (approved)");

            // Use approved template: teacher_registration_success
            // Template params: {{1}} = teacherName, {{2}} = communityLink
            String communityLink = whatsAppConfig.getCommunityInviteLink();
            
            WhatsAppMessageRequest request = WhatsAppMessageRequest.builder()
                    .messagingProduct("whatsapp")
                    .to(teacherPhone)
                    .type("template")
                    .template(WhatsAppMessageRequest.TemplateMessage.builder()
                            .name("teacher_registration_success")
                            .language(WhatsAppMessageRequest.TemplateMessage.Language.builder()
                                    .code("en")
                                    .build())
                            .components(Arrays.asList(
                                    WhatsAppMessageRequest.TemplateMessage.Component.builder()
                                            .type("body")
                                            .parameters(Arrays.asList(
                                                    WhatsAppMessageRequest.TemplateMessage.Component.Parameter.builder()
                                                            .type("text")
                                                            .text(teacher.getFullName())
                                                            .build(),
                                                    WhatsAppMessageRequest.TemplateMessage.Component.Parameter.builder()
                                                            .type("text")
                                                            .text(communityLink)
                                                            .build()
                                            ))
                                            .build()
                            ))
                            .build())
                    .build();

            sendMessage(request);
            
            log.info("✅ Sent teacher registration success using template to: {} ({})", teacher.getFullName(), teacherPhone);
        } catch (Exception e) {
            log.error("Failed to add teacher to community: {}", e.getMessage(), e);
        }
    }
    
    /**
     * 5. Handle teacher interest callback
     * When teacher clicks "I'm Interested" button
     */
    public void handleTeacherInterest(String teacherPhone, Long inquiryId) {
        try {
            String adminPhone = formatPhoneNumber(whatsAppConfig.getAdminPhone());
            String formattedTeacherPhone = formatPhoneNumber(teacherPhone);
            
            // Notify admin about teacher interest
            String adminMessage = String.format(
                    "👨‍🏫 *TEACHER INTEREST ALERT*\n\n" +
                    "A teacher has expressed interest in:\n\n" +
                    "📋 Inquiry ID: *#%s*\n" +
                    "📱 Teacher Phone: %s\n\n" +
                    "Please review the teacher profile and proceed with assignment.",
                    inquiryId,
                    formattedTeacherPhone
            );

            WhatsAppMessageRequest adminRequest = WhatsAppMessageRequest.builder()
                    .messagingProduct("whatsapp")
                    .to(adminPhone)
                    .type("text")
                    .text(WhatsAppMessageRequest.TextMessage.builder()
                            .previewUrl(false)
                            .body(adminMessage)
                            .build())
                    .build();

            sendMessage(adminRequest);

            // Confirm to teacher
            String teacherMessage = String.format(
                    "✅ *Interest Recorded!*\n\n" +
                    "Thank you for showing interest in Inquiry #%s.\n\n" +
                    "Our admin team will review your profile and contact you shortly if you're selected.\n\n" +
                    "Good luck! 🍀",
                    inquiryId
            );

            WhatsAppMessageRequest teacherRequest = WhatsAppMessageRequest.builder()
                    .messagingProduct("whatsapp")
                    .to(formattedTeacherPhone)
                    .type("text")
                    .text(WhatsAppMessageRequest.TextMessage.builder()
                            .previewUrl(false)
                            .body(teacherMessage)
                            .build())
                    .build();

            sendMessage(teacherRequest);

            log.info("Processed teacher interest for inquiry #{}", inquiryId);
        } catch (Exception e) {
            log.error("Failed to handle teacher interest: {}", e.getMessage());
        }
    }

    /**
     * Core method to send WhatsApp message via Cloud API
     */
    private void sendMessage(WhatsAppMessageRequest request) {
        try {
            log.debug("==================== WHATSAPP MESSAGE DEBUG START ====================");
            
            // Log configuration
            log.debug("WhatsApp Config - API URL: {}", whatsAppConfig.getWhatsappApiUrl());
            log.debug("WhatsApp Config - Phone Number ID: {}", whatsAppConfig.getPhoneNumberId());
            log.debug("WhatsApp Config - Messages Endpoint: {}", whatsAppConfig.getMessagesEndpoint());
            log.debug("WhatsApp Config - Admin Phone: {}", whatsAppConfig.getAdminPhone());
            log.debug("WhatsApp Config - Access Token (first 20 chars): {}...", 
                whatsAppConfig.getAccessToken() != null ? whatsAppConfig.getAccessToken().substring(0, Math.min(20, whatsAppConfig.getAccessToken().length())) : "NULL");
            
            // Log request details
            log.debug("Request - To: {}", request.getTo());
            log.debug("Request - Type: {}", request.getType());
            log.debug("Request - Messaging Product: {}", request.getMessagingProduct());
            if (request.getText() != null) {
                log.debug("Request - Message Body Length: {} chars", request.getText().getBody() != null ? request.getText().getBody().length() : 0);
                log.debug("Request - Message Preview: {}...", request.getText().getBody() != null && request.getText().getBody().length() > 50 
                    ? request.getText().getBody().substring(0, 50) : request.getText().getBody());
            }
            
            // Prepare headers
            log.debug("Preparing HTTP headers...");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(whatsAppConfig.getAccessToken());
            log.debug("Headers prepared with Bearer authentication");

            // Create request entity
            log.debug("Creating HTTP entity...");
            HttpEntity<WhatsAppMessageRequest> entity = new HttpEntity<>(request, headers);
            log.debug("HTTP entity created successfully");

            // Make API call
            log.debug("Making POST request to: {}", whatsAppConfig.getMessagesEndpoint());
            ResponseEntity<String> response = restTemplate.exchange(
                    whatsAppConfig.getMessagesEndpoint(),
                    HttpMethod.POST,
                    entity,
                    String.class
            );
            
            // Log response
            log.debug("Response Status Code: {}", response.getStatusCode());
            log.debug("Response Status Code Value: {}", response.getStatusCode().value());
            log.debug("Response Body: {}", response.getBody());
            log.debug("Response Headers: {}", response.getHeaders());

            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("✅ WhatsApp message sent successfully to: {}", request.getTo());
                log.debug("Response details - Status: {}, Body: {}", response.getStatusCode(), response.getBody());
            } else {
                log.error("❌ Failed to send WhatsApp message. Status: {}, Body: {}", 
                    response.getStatusCode(), response.getBody());
            }
            
            log.debug("==================== WHATSAPP MESSAGE DEBUG END ====================");
        } catch (Exception e) {
            log.error("==================== WHATSAPP ERROR ====================");
            log.error("❌ Exception occurred while sending WhatsApp message");
            log.error("Error Type: {}", e.getClass().getName());
            log.error("Error Message: {}", e.getMessage());
            log.error("Stack Trace: ", e);
            log.error("Request Details - To: {}, Type: {}", request.getTo(), request.getType());
            log.error("Endpoint: {}", whatsAppConfig.getMessagesEndpoint());
            log.error("==================== WHATSAPP ERROR END ====================");
            throw new RuntimeException("Failed to send WhatsApp message", e);
        }
    }

    /**
     * Convert minutes to readable time format
     * Example: 540 -> "09:00 AM", 840 -> "02:00 PM", 1020 -> "05:00 PM"
     */
    private String formatTimeFromMinutes(Integer minutes) {
        if (minutes == null) {
            return "Not specified";
        }
        
        int hours = minutes / 60;
        int mins = minutes % 60;
        
        String period = hours >= 12 ? "PM" : "AM";
        int displayHour = hours % 12;
        if (displayHour == 0) displayHour = 12;
        
        return String.format("%02d:%02d %s", displayHour, mins, period);
    }

    /**
     * Format phone number to WhatsApp format (with country code, no +)
     * Example: +919876543210 -> 919876543210
     */
    private String formatPhoneNumber(String phone) {
        if (phone == null) {
            throw new IllegalArgumentException("Phone number cannot be null");
        }
        
        // Remove all non-digit characters
        String cleaned = phone.replaceAll("[^0-9]", "");
        
        // If doesn't start with country code, add India code (91)
        if (cleaned.length() == 10) {
            cleaned = "91" + cleaned;
        }
        
        return cleaned;
    }
}
