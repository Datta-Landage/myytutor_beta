package com.myytutor.service;

import com.myytutor.dto.DocumentResponseDto;
import com.myytutor.dto.InquiryRequest;
import com.myytutor.entity.*;
import com.myytutor.entity.Document.DocumentType;
import com.myytutor.repository.*;
import com.myytutor.util.HtmlSanitizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class InquiryService {
    private static final Logger log = LoggerFactory.getLogger(InquiryService.class);

    @Autowired
    private InquiryRepository inquiryRepository;

    @Autowired
    private SubjectClassRepository subjectClassRepository;

    @Autowired
    private ExtraSubjectRepository extraSubjectRepository;

    @Autowired
    private InquirySubjectClassMappingRepository subjectMappingRepository;

    @Autowired
    private InquiryExtraSubjectMappingRepository extraSubjectMappingRepository;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private WhatsAppService whatsAppService;

    @Autowired
    private com.myytutor.service.EmailService emailService;

    @Autowired
    private HtmlSanitizer htmlSanitizer;

    @Value("${app.rate-limit.ip-check-enabled:true}")
    private boolean rateLimitEnabled;

    /**
     * Convert minutes from start of day to 24-hour format (HH:MM)
     * Example: 540 -> 09:00, 1020 -> 17:00
     */
    private String formatTimeFromMinutes(Integer minutes) {
        if (minutes == null)
            return "Not specified";
        int hours = minutes / 60;
        int mins = minutes % 60;
        return String.format("%02d:%02d", hours, mins);
    }

    private void validateSubjects(List<Long> subjectIds, List<Long> extraSubjectIds) {
        boolean hasRegular = subjectIds != null && !subjectIds.isEmpty();
        boolean hasExtra = extraSubjectIds != null && !extraSubjectIds.isEmpty();

        if (!hasRegular && !hasExtra) {
            log.error("No subjects selected in the inquiry request");
            throw new IllegalArgumentException("At least one subject must be selected");
        }

        // Check if regular subjects exist
        if (hasRegular) {
            List<SubjectClass> subjects = subjectClassRepository.findAllById(subjectIds);
            if (subjects.size() != subjectIds.size()) {
                List<Long> existingIds = subjects.stream().map(SubjectClass::getId).toList();
                List<Long> missingIds = subjectIds.stream().filter(id -> !existingIds.contains(id)).toList();
                log.error("Invalid regular subject IDs in request: {}", missingIds);
                throw new IllegalArgumentException(String.format("The following subject IDs do not exist: %s", missingIds));
            }
        }

        // Check if extra subjects exist
        if (hasExtra) {
            List<ExtraSubject> extras = extraSubjectRepository.findAllById(extraSubjectIds);
            if (extras.size() != extraSubjectIds.size()) {
                List<Long> existingIds = extras.stream().map(ExtraSubject::getId).toList();
                List<Long> missingIds = extraSubjectIds.stream().filter(id -> !existingIds.contains(id)).toList();
                log.error("Invalid extra subject IDs in request: {}", missingIds);
                throw new IllegalArgumentException(String.format("The following extra subject IDs do not exist: %s", missingIds));
            }
        }
        log.debug("Successfully validated subjects (Regular: {}, Extra: {})", 
            hasRegular ? subjectIds.size() : 0, hasExtra ? extraSubjectIds.size() : 0);
    }

    @Transactional(timeout = 30)
    public Inquiry createInquiry(InquiryRequest req) {
        log.info("Creating new inquiry for {}", req.getName());

        // Validate required fields
        if (req.getPhone() == null || req.getPhone().trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number is required");
        }

        // Check inquiry limit per phone number (5 per day)
        // ONLY checks if rate limiting is ENABLED
        if (rateLimitEnabled) {
            LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
            LocalDateTime endOfDay = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59).withNano(999999999);
            long inquiryCount = inquiryRepository.countByPhoneAndCreatedAtBetween(req.getPhone(), startOfDay, endOfDay);
    
            if (inquiryCount >= 5) {
                log.warn("Phone {} exceeded daily inquiry limit: {} inquiries today", req.getPhone(), inquiryCount);
                throw new IllegalArgumentException(
                        "You have reached the maximum limit of 5 inquiries per day. Please try again tomorrow.");
            }
            log.debug("Phone {} has {} inquiries today (limit: 5)", req.getPhone(), inquiryCount);
        } else {
            log.info("Rate limiting disabled - skipping daily limit check for phone {}", req.getPhone());
        }

        // Validate privacy policy acceptance
        if (!req.isPrivacyAccepted()) {
            throw new IllegalArgumentException("Privacy policy must be accepted");
        }

        try {
            // Verify privacy policy version matches latest version
            DocumentResponseDto latestPolicy = documentService.getLatest(DocumentType.PRIVACY_POLICY);
            if (!latestPolicy.getVersion().equals(req.getPrivacyVersion())) {
                throw new IllegalArgumentException(
                        "Privacy policy version is outdated. Please accept the latest version: "
                                + latestPolicy.getVersion());
            }
        } catch (NoSuchElementException e) {
            log.warn("No privacy policy found in database");
            throw new IllegalStateException("Privacy policy is not configured in the system");
        }

        // Validate Terms of Use acceptance
        if (!req.isTermsAccepted()) {
            throw new IllegalArgumentException("Terms of Use must be accepted");
        }

        try {
            DocumentResponseDto latestTerms = documentService.getLatest(DocumentType.TERMS_OF_USE);
            if (!latestTerms.getVersion().equals(req.getTermsVersion())) {
                throw new IllegalArgumentException(
                        "Terms of Use version is outdated. Please accept the latest version: "
                                + latestTerms.getVersion());
            }
        } catch (NoSuchElementException e) {
            log.warn("No Terms of Use found in database");
            throw new IllegalStateException("Terms of Use is not configured in the system");
        }

        // Validate User Agreement acceptance
        if (!req.isUserAgreementAccepted()) {
            throw new IllegalArgumentException("User Agreement must be accepted");
        }

        try {
            DocumentResponseDto latestAgreement = documentService.getLatest(DocumentType.USER_AGREEMENT);
            if (!latestAgreement.getVersion().equals(req.getUserAgreementVersion())) {
                throw new IllegalArgumentException(
                        "User Agreement version is outdated. Please accept the latest version: "
                                + latestAgreement.getVersion());
            }
        } catch (NoSuchElementException e) {
            log.warn("No User Agreement found in database");
            throw new IllegalStateException("User Agreement is not configured in the system");
        }

        // Validate subjects
        validateSubjects(req.getSelectedSubjectIds(), req.getSelectedExtraSubjectIds());

        // Sanitize user inputs to prevent XSS attacks
        String sanitizedName = htmlSanitizer.sanitizeNotEmpty(req.getName());
        String sanitizedAddress = htmlSanitizer.sanitizeNotEmpty(req.getAddress());
        String sanitizedMessage = htmlSanitizer.sanitize(req.getMessage()); // Message can be empty

        if (sanitizedName == null) {
            throw new IllegalArgumentException("Name cannot be empty after removing invalid characters");
        }
        if (sanitizedAddress == null) {
            throw new IllegalArgumentException("Address cannot be empty after removing invalid characters");
        }

        // Create and save the main inquiry
        final Inquiry inquiry = new Inquiry();
        inquiry.setName(sanitizedName);
        inquiry.setPhone(req.getPhone());
        inquiry.setClassStandard(req.getClassStandard());
        inquiry.setBoard(req.getBoard());
        inquiry.setAddress(sanitizedAddress);
        inquiry.setMessage(sanitizedMessage);
        inquiry.setSelectedStartDate(req.getSelectedStartDate());
        inquiry.setSelectedEndDate(req.getSelectedEndDate());
        inquiry.setSelectedStartTime(req.getSelectedStartTime());
        inquiry.setSelectedEndTime(req.getSelectedEndTime());
        inquiry.setPrivacyAccepted(req.isPrivacyAccepted());
        inquiry.setPrivacyVersion(req.getPrivacyVersion());
        inquiry.setPrivacyAcceptedAt(LocalDateTime.now());
        inquiry.setTermsAccepted(req.isTermsAccepted());
        inquiry.setTermsVersion(req.getTermsVersion());
        inquiry.setTermsAcceptedAt(LocalDateTime.now());
        inquiry.setUserAgreementAccepted(req.isUserAgreementAccepted());
        inquiry.setUserAgreementVersion(req.getUserAgreementVersion());
        inquiry.setUserAgreementAcceptedAt(LocalDateTime.now());
        inquiry.setCreatedAt(LocalDateTime.now());
        inquiry.setUpdatedAt(LocalDateTime.now());

        // Handle subject mappings
        if (req.getSelectedSubjectIds() != null && !req.getSelectedSubjectIds().isEmpty()) {
            for (Long subjectId : req.getSelectedSubjectIds()) {
                subjectClassRepository.findById(subjectId).ifPresent(subjectClass -> {
                    InquirySubjectClassMapping mapping = new InquirySubjectClassMapping();
                    mapping.setInquiry(inquiry);
                    mapping.setSubjectClass(subjectClass);
                    inquiry.getSubjectMappings().add(mapping);
                });
            }
            log.info("Added {} subject mappings to collection", req.getSelectedSubjectIds().size());
        }

        // Handle extra subject mappings
        if (req.getSelectedExtraSubjectIds() != null && !req.getSelectedExtraSubjectIds().isEmpty()) {
            for (Long extraSubjectId : req.getSelectedExtraSubjectIds()) {
                extraSubjectRepository.findById(extraSubjectId).ifPresent(extraSubject -> {
                    InquiryExtraSubjectMapping mapping = new InquiryExtraSubjectMapping();
                    mapping.setInquiry(inquiry);
                    mapping.setExtraSubject(extraSubject);
                    inquiry.getExtraSubjectMappings().add(mapping);
                });
            }
            log.info("Added {} extra subject mappings to collection", req.getSelectedExtraSubjectIds().size());
        }

        final Inquiry savedInquiry = inquiryRepository.save(inquiry);
        log.info("Saved inquiry with ID: {} and its mappings", savedInquiry.getId());

        // Send WhatsApp notifications (async - don't fail if WhatsApp fails)
        try {
            // 1. Send confirmation to customer
            whatsAppService.sendInquiryConfirmation(savedInquiry.getPhone(), savedInquiry.getName(), 
                savedInquiry.getId() != null ? savedInquiry.getId().toString() : "N/A");

            // Small delay to avoid rate limiting in test mode
            Thread.sleep(1000);

            // 2. Broadcast to teacher community (short summary to admin)
            whatsAppService.broadcastInquiryToCommunity(savedInquiry);

            // Small delay between messages to same number (test mode limitation)
            Thread.sleep(2000);

            // 3. Send full details to admin
            whatsAppService.sendFullInquiryToAdmin(savedInquiry);
        } catch (Exception e) {
            log.error("Failed to send WhatsApp notifications for inquiry {}: {}", 
                savedInquiry.getId(), e.getMessage());
            // Don't fail inquiry creation if WhatsApp fails
        }

        // Send full inquiry to consultant via email (copy-paste friendly)
        try {
            // Format time to 24-hour format for email display
            String startTime = formatTimeFromMinutes(savedInquiry.getSelectedStartTime());
            String endTime = formatTimeFromMinutes(savedInquiry.getSelectedEndTime());
            String formattedTimeWindow = startTime + " - " + endTime;

            emailService.sendConsultantInquiry(savedInquiry, formattedTimeWindow);
        } catch (Exception e) {
            log.error("Failed to send consultant email for inquiry {}: {}", 
                savedInquiry.getId(), e.getMessage());
        }

        return savedInquiry;
    }
}
