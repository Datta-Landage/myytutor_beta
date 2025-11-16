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
     */
    public void sendInquiryConfirmation(String customerPhone, String inquiryId) {
        try {
            String formattedPhone = formatPhoneNumber(customerPhone);
            
            WhatsAppMessageRequest request = WhatsAppMessageRequest.builder()
                    .messagingProduct("whatsapp")
                    .to(formattedPhone)
                    .type("text")
                    .text(WhatsAppMessageRequest.TextMessage.builder()
                            .previewUrl(false)
                            .body(String.format(
                                    "✅ Thank you for your inquiry! (ID: %s)\n\n" +
                                    "We have received your tutoring request. Our team will connect with you soon.\n\n" +
                                    "📞 You can expect a call within 24 hours.\n\n" +
                                    "Best regards,\n" +
                                    "MYY Tutor Team",
                                    inquiryId
                            ))
                            .build())
                    .build();

            sendMessage(request);
            log.info("Sent inquiry confirmation to customer: {}", customerPhone);
        } catch (Exception e) {
            log.error("Failed to send inquiry confirmation to {}: {}", customerPhone, e.getMessage());
        }
    }

    /**
     * 2. Send inquiry messages to admin (2 messages)
     * Message 1: Full details for admin
     * Message 2: Shareable message for admin to forward to community
     */
    public void broadcastInquiryToCommunity(Inquiry inquiry) {
        try {
            String adminPhone = formatPhoneNumber(whatsAppConfig.getAdminPhone());
            String communityLink = whatsAppConfig.getCommunityId();
            
            // Get first subject from mappings
            String subjects = inquiry.getSubjectMappings() != null && !inquiry.getSubjectMappings().isEmpty()
                    ? inquiry.getSubjectMappings().iterator().next().getSubjectClass().getSubjectName()
                    : "Multiple Subjects";

            // Message to forward to community (no personal details)
            String communityMessage = String.format(
                    "🔔 *NEW INQUIRY ALERT*\n\n" +
                    "📋 Inquiry ID: *#%s*\n" +
                    "📚 Subject: *%s*\n" +
                    "🏫 Class: *%s*\n" +
                    "📍 Area: *%s*\n" +
                    "⏰ Start Date: *%s*\n\n" +
                    "💡 *Interested?*\n" +
                    "Reply to this message with: *YES*\n\n" +
                    "_Forward this message to teacher community_",
                    inquiry.getId(),
                    subjects,
                    inquiry.getClassStandard(),
                    inquiry.getAddress(),
                    inquiry.getSelectedStartDate() != null ? inquiry.getSelectedStartDate().toString() : "Flexible"
            );

            WhatsAppMessageRequest communityRequest = WhatsAppMessageRequest.builder()
                    .messagingProduct("whatsapp")
                    .to(adminPhone)
                    .type("text")
                    .text(WhatsAppMessageRequest.TextMessage.builder()
                            .previewUrl(false)
                            .body(communityMessage)
                            .build())
                    .build();

            sendMessage(communityRequest);
            log.info("✅ Sent shareable inquiry message to admin for inquiry #{}", inquiry.getId());
            
            // Small delay between messages
            Thread.sleep(500);
            
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
            
            // Get all subjects
            StringBuilder subjectsList = new StringBuilder();
            if (inquiry.getSubjectMappings() != null && !inquiry.getSubjectMappings().isEmpty()) {
                inquiry.getSubjectMappings().forEach(mapping -> 
                    subjectsList.append("  • ").append(mapping.getSubjectClass().getSubjectName()).append("\n")
                );
            }
            if (inquiry.getExtraSubjectMappings() != null && !inquiry.getExtraSubjectMappings().isEmpty()) {
                inquiry.getExtraSubjectMappings().forEach(mapping -> 
                    subjectsList.append("  • ").append(mapping.getExtraSubject().getExtraSubjectName()).append(" (Extra)\n")
                );
            }

            String message = String.format(
                    "📨 *NEW INQUIRY - FULL DETAILS*\n\n" +
                    "🆔 Inquiry ID: *#%s*\n" +
                    "━━━━━━━━━━━━━━━━━━\n\n" +
                    "👤 *Student Information:*\n" +
                    "• Name: %s\n" +
                    "• Phone: %s\n" +
                    "• Standard: %s\n" +
                    "• Board: %s\n\n" +
                    "� *Requested Subjects:*\n" +
                    "%s\n" +
                    "📍 *Location:*\n" +
                    "• Address: %s\n\n" +
                    "⏰ *Schedule:*\n" +
                    "• Start Date: %s\n" +
                    "• End Date: %s\n" +
                    "• Start Time: %s\n" +
                    "• End Time: %s\n\n" +
                    "📝 *Additional Message:*\n" +
                    "%s\n\n" +
                    "━━━━━━━━━━━━━━━━━━\n" +
                    "⏱️ Received: %s",
                    inquiry.getId(),
                    inquiry.getName(),
                    inquiry.getPhone(),
                    inquiry.getClassStandard(),
                    inquiry.getBoard() != null ? inquiry.getBoard() : "Not specified",
                    subjectsList.toString().trim(),
                    inquiry.getAddress(),
                    inquiry.getSelectedStartDate() != null ? inquiry.getSelectedStartDate().toString() : "Not specified",
                    inquiry.getSelectedEndDate() != null ? inquiry.getSelectedEndDate().toString() : "Not specified",
                    inquiry.getSelectedStartTime() != null ? inquiry.getSelectedStartTime().toString() : "Not specified",
                    inquiry.getSelectedEndTime() != null ? inquiry.getSelectedEndTime().toString() : "Not specified",
                    inquiry.getMessage() != null ? inquiry.getMessage() : "None",
                    inquiry.getCreatedAt()
            );

            WhatsAppMessageRequest request = WhatsAppMessageRequest.builder()
                    .messagingProduct("whatsapp")
                    .to(adminPhone)
                    .type("text")
                    .text(WhatsAppMessageRequest.TextMessage.builder()
                            .previewUrl(false)
                            .body(message)
                            .build())
                    .build();

            sendMessage(request);
            log.info("Sent full inquiry details to admin for inquiry #{}", inquiry.getId());
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
            log.debug("Using hello_world template (approved template)");

            // Use approved template - hello_world for testing
            // TODO: Create custom teacher_welcome template in Meta Business Suite
            WhatsAppMessageRequest request = WhatsAppMessageRequest.builder()
                    .messagingProduct("whatsapp")
                    .to(teacherPhone)
                    .type("template")
                    .template(WhatsAppMessageRequest.TemplateMessage.builder()
                            .name("hello_world")
                            .language(WhatsAppMessageRequest.TemplateMessage.Language.builder()
                                    .code("en_US")
                                    .build())
                            .build())
                    .build();

            sendMessage(request);
            
            log.info("Sent community invitation to teacher: {} ({})", teacher.getFullName(), teacherPhone);
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
            log.debug("Headers prepared - Content-Type: {}, Authorization: Bearer {}...", 
                MediaType.APPLICATION_JSON, 
                whatsAppConfig.getAccessToken() != null ? whatsAppConfig.getAccessToken().substring(0, Math.min(20, whatsAppConfig.getAccessToken().length())) : "NULL");

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
