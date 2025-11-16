package com.myytutor.service;

import com.myytutor.dto.DocumentResponseDto;
import com.myytutor.dto.InquiryRequest;
import com.myytutor.entity.*;
import com.myytutor.entity.Document.DocumentType;
import com.myytutor.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    private void validateSubjects(List<Long> subjectIds) {
        if (subjectIds == null || subjectIds.isEmpty()) {
            log.error("No subjects selected in the inquiry request");
            throw new IllegalArgumentException("At least one subject must be selected");
        }

        // Check if all subjects exist
        List<SubjectClass> subjects = subjectClassRepository.findAllById(subjectIds);
        if (subjects.size() != subjectIds.size()) {
            // Find which subject IDs don't exist
            List<Long> existingIds = subjects.stream()
                    .map(SubjectClass::getId)
                    .toList();
            List<Long> missingIds = subjectIds.stream()
                    .filter(id -> !existingIds.contains(id))
                    .toList();

            log.error("Invalid subject IDs in request: {}", missingIds);
            throw new IllegalArgumentException(
                    String.format("The following subject IDs do not exist in the system: %s", missingIds));
        }
        log.debug("Successfully validated {} subjects", subjects.size());
    }

    @Transactional
    public Inquiry createInquiry(InquiryRequest req) {
        log.info("Creating new inquiry for {}", req.getName());

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

        // Validate subjects
        validateSubjects(req.getSelectedSubjectIds());

        // Create and save the main inquiry
        final Inquiry inquiry = new Inquiry();
        inquiry.setName(req.getName());
        inquiry.setPhone(req.getPhone());
        inquiry.setClassStandard(req.getClassStandard());
        inquiry.setBoard(req.getBoard());
        inquiry.setAddress(req.getAddress());
        inquiry.setMessage(req.getMessage());
        inquiry.setSelectedStartDate(req.getSelectedStartDate());
        inquiry.setSelectedEndDate(req.getSelectedEndDate());
        inquiry.setSelectedStartTime(req.getSelectedStartTime());
        inquiry.setSelectedEndTime(req.getSelectedEndTime());
        inquiry.setPrivacyAccepted(req.isPrivacyAccepted());
        inquiry.setPrivacyVersion(req.getPrivacyVersion());
        inquiry.setPrivacyAcceptedAt(LocalDateTime.now());
        inquiry.setCreatedAt(LocalDateTime.now());

        inquiryRepository.save(inquiry);
        log.info("Saved inquiry with ID: {}", inquiry.getId());

        // Handle subject mappings
        if (req.getSelectedSubjectIds() != null && !req.getSelectedSubjectIds().isEmpty()) {
            for (Long subjectId : req.getSelectedSubjectIds()) {
                subjectClassRepository.findById(subjectId).ifPresent(subjectClass -> {
                    InquirySubjectClassMapping mapping = new InquirySubjectClassMapping();
                    mapping.setInquiry(inquiry);
                    mapping.setSubjectClass(subjectClass);
                    subjectMappingRepository.save(mapping);
                });
            }
            log.info("Added {} subject mappings", req.getSelectedSubjectIds().size());
        }

        // Handle extra subject mappings
        if (req.getSelectedExtraSubjectIds() != null && !req.getSelectedExtraSubjectIds().isEmpty()) {
            for (Long extraSubjectId : req.getSelectedExtraSubjectIds()) {
                extraSubjectRepository.findById(extraSubjectId).ifPresent(extraSubject -> {
                    InquiryExtraSubjectMapping mapping = new InquiryExtraSubjectMapping();
                    mapping.setInquiry(inquiry);
                    mapping.setExtraSubject(extraSubject);
                    extraSubjectMappingRepository.save(mapping);
                });
            }
            log.info("Added {} extra subject mappings", req.getSelectedExtraSubjectIds().size());
        }

        // Send WhatsApp notifications (async - don't fail if WhatsApp fails)
        try {
            // 1. Send confirmation to customer
            whatsAppService.sendInquiryConfirmation(inquiry.getPhone(), inquiry.getId().toString());
            
            // 2. Broadcast to teacher community
            whatsAppService.broadcastInquiryToCommunity(inquiry);
            
            // 3. Send full details to admin
            whatsAppService.sendFullInquiryToAdmin(inquiry);
        } catch (Exception e) {
            log.error("Failed to send WhatsApp notifications for inquiry {}: {}", inquiry.getId(), e.getMessage());
            // Don't fail inquiry creation if WhatsApp fails
        }

        return inquiry;
    }
}
