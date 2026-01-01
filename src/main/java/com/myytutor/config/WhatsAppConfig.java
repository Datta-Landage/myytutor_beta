package com.myytutor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * WhatsApp Business Cloud API Configuration
 * Documentation: https://developers.facebook.com/docs/whatsapp/cloud-api
 */
@Configuration
public class WhatsAppConfig {

    @Value("${whatsapp.api.url:https://graph.facebook.com/v22.0}")
    private String whatsappApiUrl;

    @Value("${whatsapp.phone.number.id}")
    private String phoneNumberId;

    @Value("${whatsapp.access.token}")
    private String accessToken;

    @Value("${whatsapp.admin.phone}")
    private String adminPhone;

    @Value("${whatsapp.community.id:}")
    private String communityId;

    @Value("${whatsapp.community.invite.link:}")
    private String communityInviteLink;

    @Value("${whatsapp.verify.token}")
    private String verifyToken;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public String getWhatsappApiUrl() {
        return whatsappApiUrl;
    }

    public String getPhoneNumberId() {
        return phoneNumberId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getAdminPhone() {
        return adminPhone;
    }

    public String getCommunityId() {
        return communityId;
    }

    public String getCommunityInviteLink() {
        // Return invite link if set, else fallback to community ID (which is the full
        // URL)
        if (communityInviteLink != null && !communityInviteLink.trim().isEmpty()) {
            return communityInviteLink;
        }
        return communityId;
    }

    public String getVerifyToken() {
        return verifyToken;
    }

    public String getMessagesEndpoint() {
        return whatsappApiUrl + "/" + phoneNumberId + "/messages";
    }
}
