package com.myytutor.service;

import com.myytutor.dto.*;
import com.myytutor.entity.*;
import com.myytutor.repository.*;
import com.myytutor.exception.ResourceNotFoundException;
import com.myytutor.util.TeacherEducationConverter;
import java.util.stream.Collectors;
import com.myytutor.repository.DocumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class TeacherService {
    private static final Logger log = LoggerFactory.getLogger(TeacherService.class);

    @Autowired
    private TeacherRepository teacherRepository;
    
    @Autowired
    private TeacherAvailabilityRepository teacherAvailabilityRepository;
    
    @Autowired
    private TeacherEducationRepository teacherEducationRepository;
    
    @Autowired
    private TeacherSubjectMappingRepository subjectMappingRepository;
    
    @Autowired
    private TeacherExtraSubjectMappingRepository extraSubjectMappingRepository;
    
    @Autowired
    private TeacherPreferredAreaMappingRepository preferredAreaMappingRepository;
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private WhatsAppService whatsAppService;
    
    @Autowired
    private DocumentService documentService;
    
    @Autowired
    private DocumentRepository repo;

    @Autowired
    private SubjectClassRepository subjectClassRepository;
    
    @Autowired
    private ExtraSubjectRepository extraSubjectRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final SecureRandom random = new SecureRandom();

    public void sendVerificationOtp(TeacherEmailVerificationRequest req) {
        // Check if email already registered and verified
        Teacher existing = teacherRepository.findByEmail(req.getEmail());
        if (existing != null && Boolean.TRUE.equals(existing.getEmailVerified())) {
            throw new IllegalStateException("Email already registered and verified");
        }

        // Generate 6-digit OTP
        String otp = String.format("%06d", random.nextInt(1000000));

        // Create or update temporary teacher record
        Teacher teacher = existing != null ? existing : new Teacher();
        teacher.setEmail(req.getEmail());
        teacher.setEmailOtp(otp);
        teacher.setEmailOtpGeneratedAt(LocalDateTime.now());
        teacherRepository.save(teacher);

        // Send OTP via email
        emailService.sendOtp(req.getEmail(), otp);
        log.info("Sent verification OTP to: {}", req.getEmail());
    }

    public void verifyOtp(TeacherOtpVerificationRequest req) {
        Teacher teacher = teacherRepository.findByEmail(req.getEmail());
        if (teacher == null) {
            throw new IllegalArgumentException("Email not found");
        }

        // Check if email is already verified
        if (Boolean.TRUE.equals(teacher.getEmailVerified())) {
            throw new IllegalArgumentException("Email is already verified. Please proceed with registration.");
        }

        // Check if OTP exists
        if (teacher.getEmailOtp() == null || teacher.getEmailOtpGeneratedAt() == null) {
            throw new IllegalArgumentException("No OTP found. Please request a new OTP.");
        }

        // Check OTP expiry (5 minutes)
        if (teacher.getEmailOtpGeneratedAt().plusMinutes(5).isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("OTP has expired. Please request a new one.");
        }

        // Verify OTP
        if (!teacher.getEmailOtp().equals(req.getOtp())) {
            throw new IllegalArgumentException("Invalid OTP");
        }

        // Mark email as verified
        teacher.setEmailVerified(true);
        teacher.setEmailVerifiedAt(LocalDateTime.now());
        teacher.setEmailOtp(null); // Clear OTP after verification
        teacher.setEmailOtpGeneratedAt(null);
        teacherRepository.save(teacher);
        
        // Send verification success email
        emailService.sendVerificationSuccess(req.getEmail(), teacher.getFullName() != null ? teacher.getFullName() : "User");
        log.info("Email verified for: {}", req.getEmail());
    }

    @Transactional
    public Teacher registerTeacher(TeacherRegistrationRequest req) {
        // 1. Verify email is validated
        Teacher teacher = teacherRepository.findByEmail(req.getEmail());
        if (teacher == null || !Boolean.TRUE.equals(teacher.getEmailVerified())) {
            throw new IllegalArgumentException("Email not verified. Please verify your email first.");
        }
        
        // 2. Check if teacher already completed full registration
        if (teacherRepository.existsByEmailAndFullyRegistered(req.getEmail())) {
            log.warn("Attempt to re-register already completed teacher account: {}", req.getEmail());
            throw new IllegalStateException(
                "This email is already registered with a complete teacher account. " +
                "Please login instead of registering again."
            );
        }

        // 3. Validate and update agreements
        validateAndUpdateAgreements(teacher, req.getTeacherAgreement());

        // 4. Update teacher details
        if (req.getPassword() != null && !req.getPassword().isBlank()) {
            teacher.setPassword(passwordEncoder.encode(req.getPassword()));
        }
        teacher.setFullName(req.getFullName());
        teacher.setPhoneNumber(req.getPhoneNumber());
        teacher.setWhatsappNumber(req.getWhatsappNumber());
        teacher.setGender(validateGender(req.getGender()));
        teacher.setQualifications(req.getQualifications());
        teacher.setCertifications(req.getCertifications());
        teacher.setExperience(req.getExperience());
        teacher.setHasVehicle(req.getHasVehicle());
        teacher.setCity(req.getCity());
        teacher.setPin(req.getPin());
        teacher.setAddress(req.getAddress());
        teacher.setAboutMe(req.getAboutMe());
        teacher.setMode(req.getMode());
        teacher.setExpectedFeePerHour(req.getExpectedFeePerHour());

        // 5. Save basic details first
        teacher = teacherRepository.save(teacher);

        // 6. Handle preferred areas mapping
        updatePreferredAreas(teacher, req.getPreferredAreas());

        // 7. Handle subject mappings
        updateSubjectMappings(teacher, req.getSubjectIds());
        updateExtraSubjectMappings(teacher, req.getAdditionalSubjects());

        // 8. Handle availability mappings (Min: 1, Max: 3)
        updateTeacherAvailabilities(teacher, req.getAvailabilities());

        // 9. Handle education mappings (Min: 1, Max: 3)
        updateTeacherEducations(teacher, req.getEducations());

        // 10. Save the teacher with all updates (including agreements, availabilities, and educations)
        teacher = teacherRepository.save(teacher);

        // 11. Send registration success email with teacher details
        emailService.sendRegistrationSuccess(teacher.getEmail(), teacher.getFullName(), teacher);

        // 12. Add teacher to WhatsApp community (async)
        try {
            whatsAppService.addTeacherToCommunity(teacher);
        } catch (Exception e) {
            log.error("Failed to add teacher to WhatsApp community: {}", e.getMessage());
            // Don't fail registration if WhatsApp fails
        }

        log.info("Completed registration for teacher: {}", teacher.getEmail());
        return teacher;
    }

    private void updateTeacherEducations(Teacher teacher, List<TeacherEducationDTO> educationDTOs) {
        if (educationDTOs == null || educationDTOs.isEmpty()) {
            throw new IllegalArgumentException("At least one education entry is required");
        }

        if (educationDTOs.size() > 3) {
            throw new IllegalArgumentException("Maximum 3 education entries allowed");
        }

        // Validate each education entry
        educationDTOs.forEach(dto -> {
            if (dto == null) {
                throw new IllegalArgumentException("Education record cannot be null");
            }
            dto.validate(); // Custom validation in DTO
        });

        // First, remove all existing educations
        teacherEducationRepository.deleteByTeacherId(teacher.getId());

        // Add new education entries
        for (TeacherEducationDTO dto : educationDTOs) {
            TeacherEducation education = new TeacherEducation();
            education.setTeacher(teacher);
            education.setDegree(dto.getDegree());
            education.setInstitution(dto.getInstitution());
            education.setPassingYear(dto.getPassingYear());
            education.setGrade(dto.getGrade());

            // Save each education entry
            teacherEducationRepository.save(education);
        }
    }

    private void updateTeacherAvailabilities(Teacher teacher, List<TeacherAvailabilityDTO> availabilityDTOs) {
        if (availabilityDTOs == null || availabilityDTOs.isEmpty()) {
            throw new IllegalArgumentException("At least one availability slot is required");
        }

        if (availabilityDTOs.size() > 3) {
            throw new IllegalArgumentException("Maximum 3 availability slots allowed");
        }

        // First, remove all existing availabilities
        teacherAvailabilityRepository.deleteByTeacherId(teacher.getId());

        // Add new availabilities
        for (TeacherAvailabilityDTO dto : availabilityDTOs) {
            if (dto.getStartTime() >= dto.getEndTime()) {
                throw new IllegalArgumentException("End time must be after start time");
            }

            TeacherAvailability availability = new TeacherAvailability();
            availability.setTeacher(teacher);
            availability.setStartTime(dto.getStartTime());
            availability.setEndTime(dto.getEndTime());
            availability.setAvailableTimeForSlot(dto.getAvailableTimeForSlot());
            availability.setMonday(dto.getMonday());
            availability.setTuesday(dto.getTuesday());
            availability.setWednesday(dto.getWednesday());
            availability.setThursday(dto.getThursday());
            availability.setFriday(dto.getFriday());
            availability.setSaturday(dto.getSaturday());
            availability.setSunday(dto.getSunday());

            // Save each availability
            teacherAvailabilityRepository.save(availability);
        }
    }

    private void validateAndUpdateAgreements(Teacher teacher, TeacherAgreementDTO agreementDTO) {
        if (agreementDTO == null) {
            throw new IllegalArgumentException("Teacher agreement is required");
        }

        // 1. Validate acceptance
        if (!agreementDTO.getAcceptedPrivacyPolicy()) {
            throw new IllegalArgumentException("Privacy policy must be accepted");
        }
        if (!agreementDTO.getAcceptedTermsOfUse()) {
            throw new IllegalArgumentException("Terms of use must be accepted");
        }
        if (!agreementDTO.getAcceptedTeacherAgreement()) {
            throw new IllegalArgumentException("Teacher agreement must be accepted");
        }

        // 2. Create new TeacherAgreement if doesn't exist
        TeacherAgreement agreement = teacher.getAgreement();
        if (agreement == null) {
            agreement = new TeacherAgreement();
        }

        // 3. Verify and set Privacy Policy
        DocumentResponseDto latestPrivacyPolicy = documentService.getLatest(Document.DocumentType.PRIVACY_POLICY);
        if (!latestPrivacyPolicy.getVersion().equals(agreementDTO.getPrivacyPolicyVersion())) {
            log.error("Invalid Privacy Policy version provided: {}, latest version: {}", 
                    agreementDTO.getPrivacyPolicyVersion(), latestPrivacyPolicy.getVersion());
            throw new IllegalArgumentException("Must accept the latest Privacy Policy version: " + latestPrivacyPolicy.getVersion());
        }
        DocumentResponseDto ppDocDto = documentService.getByVersion(Document.DocumentType.PRIVACY_POLICY, agreementDTO.getPrivacyPolicyVersion());
        agreement.setPrivacyPolicy(repo.findByTypeAndVersion(Document.DocumentType.PRIVACY_POLICY, agreementDTO.getPrivacyPolicyVersion())
                .orElseThrow(() -> new IllegalStateException("Privacy Policy document not found after verification")));
        agreement.setPrivacyPolicyAcceptedAt(LocalDateTime.now());
        log.debug("Privacy Policy {} accepted at {}", ppDocDto.getVersion(), agreement.getPrivacyPolicyAcceptedAt());

        // 4. Verify and set Terms of Use
        DocumentResponseDto latestTerms = documentService.getLatest(Document.DocumentType.TERMS_OF_USE);
        if (!latestTerms.getVersion().equals(agreementDTO.getTermsOfUseVersion())) {
            log.error("Invalid Terms of Use version provided: {}, latest version: {}", 
                    agreementDTO.getTermsOfUseVersion(), latestTerms.getVersion());
            throw new IllegalArgumentException("Must accept the latest Terms of Use version: " + latestTerms.getVersion());
        }
        DocumentResponseDto touDocDto = documentService.getByVersion(Document.DocumentType.TERMS_OF_USE, agreementDTO.getTermsOfUseVersion());
        agreement.setTermsOfUse(repo.findByTypeAndVersion(Document.DocumentType.TERMS_OF_USE, agreementDTO.getTermsOfUseVersion())
                .orElseThrow(() -> new IllegalStateException("Terms of Use document not found after verification")));
        agreement.setTermsOfUseAcceptedAt(LocalDateTime.now());
        log.debug("Terms of Use {} accepted at {}", touDocDto.getVersion(), agreement.getTermsOfUseAcceptedAt());

        // 5. Verify and set Teacher Agreement
        DocumentResponseDto latestTeacherAgreement = documentService.getLatest(Document.DocumentType.TEACHER_AGREEMENT);
        if (!latestTeacherAgreement.getVersion().equals(agreementDTO.getTeacherAgreementVersion())) {
            log.error("Invalid Teacher Agreement version provided: {}, latest version: {}", 
                    agreementDTO.getTeacherAgreementVersion(), latestTeacherAgreement.getVersion());
            throw new IllegalArgumentException("Must accept the latest Teacher Agreement version: " + latestTeacherAgreement.getVersion());
        }
        DocumentResponseDto taDocDto = documentService.getByVersion(Document.DocumentType.TEACHER_AGREEMENT, agreementDTO.getTeacherAgreementVersion());
        agreement.setTeacherAgreement(repo.findByTypeAndVersion(Document.DocumentType.TEACHER_AGREEMENT, agreementDTO.getTeacherAgreementVersion())
                .orElseThrow(() -> new IllegalStateException("Teacher Agreement document not found after verification")));
        agreement.setTeacherAgreementAcceptedAt(LocalDateTime.now());
        log.debug("Teacher Agreement {} accepted at {}", taDocDto.getVersion(), agreement.getTeacherAgreementAcceptedAt());

        // 6. Set the validated agreement on teacher
        teacher.setAgreement(agreement);
    }

    private String validateGender(String gender) {
        if (gender == null || gender.isBlank()) {
            throw new IllegalArgumentException("Gender is required");
        }
        String g = gender.trim().toLowerCase(Locale.ROOT);
        if (!(g.equals("male") || g.equals("female") || g.equals("other"))) {
            throw new IllegalArgumentException("Gender must be Male, Female, or Other");
        }
        return g;
    }

    private void updateSubjectMappings(Teacher teacher, Set<Long> subjectIds) {
        log.info("Updating subject mappings for teacher: {}", teacher.getId());
        
        // Remove existing mappings
        subjectMappingRepository.deleteByTeacherId(teacher.getId());
        
        // Skip if no subjects
        if (subjectIds == null || subjectIds.isEmpty()) {
            log.info("No subject mappings to add for teacher: {}", teacher.getId());
            return;
        }
        
        // Add new subjects
        int addedCount = 0;
        for (Long subjectId : subjectIds) {
            subjectClassRepository.findById(subjectId).ifPresent(subjectClass -> {
                TeacherSubjectMapping mapping = new TeacherSubjectMapping();
                mapping.setTeacher(teacher);
                mapping.setSubjectClass(subjectClass);
                subjectMappingRepository.save(mapping);
            });
            addedCount++;
        }
        log.info("Added {} subject mappings for teacher: {}", addedCount, teacher.getId());
    }
    
    private void updateExtraSubjectMappings(Teacher teacher, Set<Long> extraSubjectIds) {
        log.info("Updating extra subject mappings for teacher: {}", teacher.getId());
        
        // Remove existing mappings
        extraSubjectMappingRepository.deleteByTeacherId(teacher.getId());
        
        // Skip if no extra subjects
        if (extraSubjectIds == null || extraSubjectIds.isEmpty()) {
            log.info("No extra subject mappings to add for teacher: {}", teacher.getId());
            return;
        }
        
        // Add new extra subjects
        int addedCount = 0;
        for (Long extraSubjectId : extraSubjectIds) {
            extraSubjectRepository.findById(extraSubjectId).ifPresent(extraSubject -> {
                TeacherExtraSubjectMapping mapping = new TeacherExtraSubjectMapping();
                mapping.setTeacher(teacher);
                mapping.setExtraSubject(extraSubject);
                extraSubjectMappingRepository.save(mapping);
            });
            addedCount++;
        }
        log.info("Added {} extra subject mappings for teacher: {}", addedCount, teacher.getId());
    }

    private void updatePreferredAreas(Teacher teacher, Set<String> preferredAreasSet) {
        log.info("Updating preferred areas for teacher: {}", teacher.getId());
        
        // Remove existing area mappings
        preferredAreaMappingRepository.deleteByTeacherId(teacher.getId());
        
        // Skip if no preferred areas
        if (preferredAreasSet == null || preferredAreasSet.isEmpty()) {
            log.info("No preferred areas to add for teacher: {}", teacher.getId());
            return;
        }
        
        // Add new preferred areas
        int addedCount = 0;
        for (String area : preferredAreasSet) {
            if (area != null && !area.trim().isEmpty()) {
                TeacherPreferredAreaMapping mapping = new TeacherPreferredAreaMapping();
                mapping.setTeacher(teacher);
                mapping.setArea(area.trim());
                preferredAreaMappingRepository.save(mapping);
                addedCount++;
            }
        }
        log.info("Added {} preferred area mappings for teacher: {}", addedCount, teacher.getId());
    }

    @Transactional
    public List<TeacherEducationDTO> updateTeacherEducation(Long teacherId, List<TeacherEducationDTO> educationDTOs) {
        log.info("Updating education details for teacher: {}", teacherId);
        
        // 1. Validate teacher exists
        Teacher teacher = teacherRepository.findById(teacherId)
            .orElseThrow(() -> new ResourceNotFoundException("Teacher", "id", teacherId));

        // 2. Validate input
        if (educationDTOs == null || educationDTOs.isEmpty()) {
            throw new IllegalArgumentException("At least one education record is required");
        }

        // 3. Validate each record and standardize format
        educationDTOs.forEach(dto -> {
            if (dto == null) {
                throw new IllegalArgumentException("Education record cannot be null");
            }
            dto.validate();
        });

        // 4. Delete existing education records
        teacherEducationRepository.deleteByTeacherId(teacherId);

        // 5. Convert DTOs to entities and save
        List<TeacherEducation> educationEntities = educationDTOs.stream()
            .map(dto -> {
                TeacherEducation education = TeacherEducationConverter.toEntity(dto, teacher);
                return teacherEducationRepository.save(education);
            })
            .collect(Collectors.toList());

        log.info("Successfully updated {} education records for teacher: {}", 
                educationEntities.size(), teacherId);

        // 6. Return updated records
        return TeacherEducationConverter.toDtoList(educationEntities);
    }

    @Transactional(readOnly = true)
    public List<TeacherEducationDTO> getTeacherEducation(Long teacherId) {
        log.info("Fetching education details for teacher: {}", teacherId);
        
        // Verify teacher exists
        if (!teacherRepository.existsById(teacherId)) {
            throw new ResourceNotFoundException("Teacher", "id", teacherId);
        }

        // Get education records sorted by passing year
        List<TeacherEducation> educationRecords = 
            teacherEducationRepository.findByTeacherIdOrderByPassingYearDesc(teacherId);
        
        return TeacherEducationConverter.toDtoList(educationRecords);
    }

    @Transactional
    public TeacherEducationDTO addTeacherEducation(Long teacherId, TeacherEducationDTO educationDTO) {
        log.info("Adding new education record for teacher: {}", teacherId);
        
        // 1. Validate teacher exists
        Teacher teacher = teacherRepository.findById(teacherId)
            .orElseThrow(() -> new ResourceNotFoundException("Teacher", "id", teacherId));

        // 2. Validate input
        if (educationDTO == null) {
            throw new IllegalArgumentException("Education details cannot be null");
        }
        educationDTO.validate();

        // 3. Convert and save
        TeacherEducation education = TeacherEducationConverter.toEntity(educationDTO, teacher);
        education = teacherEducationRepository.save(education);
        
        log.info("Successfully added education record for teacher: {}", teacherId);
        
        return TeacherEducationConverter.toDto(education);
    }

    @Transactional
    public void deleteTeacherEducation(Long teacherId, Long educationId) {
        log.info("Deleting education record {} for teacher: {}", educationId, teacherId);
        
        // 1. Verify the education record exists and belongs to the teacher
        TeacherEducation education = teacherEducationRepository.findById(educationId)
            .orElseThrow(() -> new ResourceNotFoundException("Education record", "id", educationId));
            
        if (!education.getTeacher().getId().equals(teacherId)) {
            throw new IllegalArgumentException("Education record does not belong to the specified teacher");
        }

        // 2. Delete the record
        teacherEducationRepository.deleteById(educationId);
        
        log.info("Successfully deleted education record {} for teacher: {}", educationId, teacherId);
    }
}

