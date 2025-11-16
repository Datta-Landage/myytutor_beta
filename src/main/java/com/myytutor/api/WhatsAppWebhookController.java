package com.myytutor.api;

import com.myytutor.config.WhatsAppConfig;
import com.myytutor.service.WhatsAppService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * WhatsApp Webhook Controller
 * Handles incoming webhooks from WhatsApp Business Cloud API
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/webhooks/whatsapp")
@RequiredArgsConstructor
public class WhatsAppWebhookController {

    private final WhatsAppConfig whatsAppConfig;
    private final WhatsAppService whatsAppService;

    /**
     * Webhook verification endpoint (required by WhatsApp)
     * GET request from Meta to verify webhook
     */
    @GetMapping
    public ResponseEntity<?> verifyWebhook(
            @RequestParam(name = "hub.mode") String mode,
            @RequestParam(name = "hub.verify_token") String token,
            @RequestParam(name = "hub.challenge") String challenge) {
        
        log.info("Webhook verification request received");
        
        if ("subscribe".equals(mode) && whatsAppConfig.getVerifyToken().equals(token)) {
            log.info("Webhook verified successfully");
            return ResponseEntity.ok(challenge);
        } else {
            log.error("Webhook verification failed");
            return ResponseEntity.status(403).body("Verification failed");
        }
    }

    /**
     * Webhook callback endpoint
     * POST request from WhatsApp when messages/events occur
     */
    @PostMapping
    public ResponseEntity<?> handleWebhook(@RequestBody Map<String, Object> payload) {
        try {
            log.info("Received WhatsApp webhook: {}", payload);

            // Extract message data
            if (payload.containsKey("entry")) {
                @SuppressWarnings("unchecked")
                var entries = (java.util.List<Map<String, Object>>) payload.get("entry");
                
                for (Map<String, Object> entry : entries) {
                    @SuppressWarnings("unchecked")
                    var changes = (java.util.List<Map<String, Object>>) entry.get("changes");
                    
                    for (Map<String, Object> change : changes) {
                        @SuppressWarnings("unchecked")
                        var value = (Map<String, Object>) change.get("value");
                        
                        // Check if it's a message event
                        if (value.containsKey("messages")) {
                            @SuppressWarnings("unchecked")
                            var messages = (java.util.List<Map<String, Object>>) value.get("messages");
                            
                            for (Map<String, Object> message : messages) {
                                processIncomingMessage(message);
                            }
                        }

                        // Check if it's a button reply (teacher interest)
                        if (value.containsKey("messages")) {
                            @SuppressWarnings("unchecked")
                            var messages = (java.util.List<Map<String, Object>>) value.get("messages");
                            
                            for (Map<String, Object> message : messages) {
                                if (message.containsKey("interactive")) {
                                    processButtonReply(message);
                                }
                            }
                        }
                    }
                }
            }

            return ResponseEntity.ok().body(Map.of("status", "received"));
        } catch (Exception e) {
            log.error("Error processing webhook: {}", e.getMessage(), e);
            return ResponseEntity.ok().body(Map.of("status", "error"));
        }
    }

    /**
     * Process incoming text messages
     */
    private void processIncomingMessage(Map<String, Object> message) {
        try {
            String from = (String) message.get("from");
            String messageId = (String) message.get("id");
            
            @SuppressWarnings("unchecked")
            var text = (Map<String, Object>) message.get("text");
            if (text != null) {
                String body = (String) text.get("body");
                log.info("Received message from {}: {}", from, body);
                
                // You can add custom logic here to handle specific commands
                // Example: if body contains "INQUIRY" keyword
            }
        } catch (Exception e) {
            log.error("Error processing incoming message: {}", e.getMessage());
        }
    }

    /**
     * Process button reply (when teacher clicks "I'm Interested")
     */
    private void processButtonReply(Map<String, Object> message) {
        try {
            String from = (String) message.get("from");
            
            @SuppressWarnings("unchecked")
            var interactive = (Map<String, Object>) message.get("interactive");
            
            if (interactive != null) {
                @SuppressWarnings("unchecked")
                var buttonReply = (Map<String, Object>) interactive.get("button_reply");
                
                if (buttonReply != null) {
                    String replyId = (String) buttonReply.get("id");
                    
                    // Parse inquiry ID from reply (format: "interest_123")
                    if (replyId != null && replyId.startsWith("interest_")) {
                        String inquiryIdStr = replyId.replace("interest_", "");
                        Long inquiryId = Long.parseLong(inquiryIdStr);
                        
                        log.info("Teacher {} expressed interest in inquiry #{}", from, inquiryId);
                        
                        // Handle teacher interest
                        whatsAppService.handleTeacherInterest(from, inquiryId);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error processing button reply: {}", e.getMessage());
        }
    }
}
