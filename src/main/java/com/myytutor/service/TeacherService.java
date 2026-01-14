package com.myytutor.service;

import com.myytutor.dto.*;
import com.myytutor.entity.*;
import com.myytutor.entity.EmailRateLimit.EmailType;
import com.myytutor.repository.*;
import com.myytutor.exception.ResourceNotFoundException;
import com.myytutor.util.TeacherEducationConverter;
import com.myytutor.util.HtmlSanitizer;
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

    @Autowired
    private HtmlSanitizer htmlSanitizer;

    @Autowired
    private EmailRateLimitService emailRateLimitService;

    @Autowired
    private SlugGeneratorService slugGeneratorService;

    private final SecureRandom random = new SecureRandom();

    public void sendVerificationOtp(TeacherEmailVerificationRequest req, String ipAddress) {
        // Check rate limiting FIRST to prevent abuse
        emailRateLimitService.checkAndRecordEmailAttempt(req.getEmail(), ipAddress, EmailType.OTP);

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
        teacher.setUpdatedAt(LocalDateTime.now());
        teacherRepository.save(teacher);

        // Send OTP via email
        emailService.sendOtp(req.getEmail(), otp);
        log.info("Sent verification OTP to: {}", req.getEmail());
    }

    @Transactional(timeout = 30)
    public void verifyOtp(TeacherOtpVerificationRequest req) {
        Teacher teacher = teacherRepository.findByEmail(req.getEmail());
        if (teacher == null) {
            throw new IllegalArgumentException("Email not found");
        }

        // Check if email is already verified
        if (Boolean.TRUE.equals(teacher.getEmailVerified())) {
            throw new IllegalArgumentException("Email is already verified. Please proceed with registration.");
        }

        // CRITICAL: Check if account is locked due to too many failed attempts
        if (teacher.getOtpLockedUntil() != null && teacher.getOtpLockedUntil().isAfter(LocalDateTime.now())) {
            long minutesRemaining = java.time.Duration.between(LocalDateTime.now(), teacher.getOtpLockedUntil())
                    .toMinutes();
            throw new IllegalStateException(
                    String.format(
                            "Too many failed OTP attempts. Account locked for %d more minutes. Please try again later.",
                            minutesRemaining));
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
            // CRITICAL: Increment failed attempts
            int attempts = (teacher.getOtpAttempts() != null ? teacher.getOtpAttempts() : 0) + 1;
            teacher.setOtpAttempts(attempts);

            // Lock account after 5 failed attempts for 30 minutes
            if (attempts >= 5) {
                teacher.setOtpLockedUntil(LocalDateTime.now().plusMinutes(30));
                teacherRepository.save(teacher);
                log.warn("Account locked for email {} after {} failed OTP attempts", req.getEmail(), attempts);
                throw new IllegalStateException(
                        "Too many failed attempts. Account locked for 30 minutes. Please try again later.");
            }

            teacherRepository.save(teacher);
            log.warn("Failed OTP verification attempt {} of 5 for email: {}", attempts, req.getEmail());
            throw new IllegalArgumentException(String.format("Invalid OTP. %d attempts remaining.", 5 - attempts));
        }

        // Mark email as verified and RESET failed attempts
        teacher.setEmailVerified(true);
        teacher.setOtpAttempts(0);
        teacher.setOtpLockedUntil(null);
        teacher.setEmailVerifiedAt(LocalDateTime.now());
        teacher.setEmailOtp(null); // Clear OTP after verification
        teacher.setEmailOtpGeneratedAt(null);
        teacherRepository.save(teacher);

        // Send verification success email
        emailService.sendVerificationSuccess(req.getEmail(),
                teacher.getFullName() != null ? teacher.getFullName() : "User");
        log.info("Email verified for: {}", req.getEmail());
    }

    @Transactional(timeout = 30)
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
                            "Please login instead of registering again.");
        }

        // 3. Validate and update agreements
        validateAndUpdateAgreements(teacher, req.getTeacherAgreement());

        // 4. Sanitize user inputs to prevent XSS attacks
        String sanitizedFullName = htmlSanitizer.sanitizeNotEmpty(req.getFullName());
        String sanitizedQualifications = htmlSanitizer.sanitize(req.getQualifications());
        String sanitizedCertifications = htmlSanitizer.sanitize(req.getCertifications());
        String sanitizedAddress = htmlSanitizer.sanitizeNotEmpty(req.getAddress());
        String sanitizedAboutMe = htmlSanitizer.sanitize(req.getAboutMe());

        if (sanitizedFullName == null) {
            throw new IllegalArgumentException("Full name cannot be empty after removing invalid characters");
        }
        if (sanitizedAddress == null) {
            throw new IllegalArgumentException("Address cannot be empty after removing invalid characters");
        }

        // 5. Update teacher details
        if (req.getPassword() != null && !req.getPassword().isBlank()) {
            // CRITICAL: Validate password complexity
            validatePasswordComplexity(req.getPassword());
            teacher.setPassword(passwordEncoder.encode(req.getPassword()));
        }
        teacher.setFullName(sanitizedFullName);
        teacher.setPhoneNumber(req.getPhoneNumber());
        teacher.setWhatsappNumber(req.getWhatsappNumber());
        teacher.setGender(validateGender(req.getGender()));
        teacher.setQualifications(sanitizedQualifications);
        teacher.setCertifications(sanitizedCertifications);
        teacher.setExperience(req.getExperience());
        teacher.setHasVehicle(req.getHasVehicle());
        teacher.setCity(req.getCity());
        teacher.setPin(req.getPin());
        teacher.setAddress(sanitizedAddress);
        teacher.setAboutMe(sanitizedAboutMe);
        teacher.setMode(req.getMode());
        teacher.setExpectedFeePerHour(req.getExpectedFeePerHour());
        teacher.setUpdatedAt(LocalDateTime.now());

        // 6. Save basic details first
        // 6. Update preferred areas mapping
        updatePreferredAreas(teacher, req.getPreferredAreas());

        // 7. Update subject mappings
        updateSubjectMappings(teacher, req.getSubjectIds());
        updateExtraSubjectMappings(teacher, req.getAdditionalSubjects());

        // 8. Update availability mappings
        updateTeacherAvailabilities(teacher, req.getAvailabilities());

        // 9. Update education mappings
        updateTeacherEducations(teacher, req.getEducations());

        // Generate slug if not present (ONCE per teacher)
        if (teacher.getSlug() == null) {
            teacher.setSlug(slugGeneratorService.generateSlug(teacher));
        }

        // 10. Save the teacher with all updates one-time (Cascading handles child entities)
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



        // Validate each education entry
        educationDTOs.forEach(dto -> {
            if (dto == null) {
                throw new IllegalArgumentException("Education record cannot be null");
            }
            dto.validate(); // Custom validation in DTO
        });

        // First, clear existing educations (Orphan removal will handle deletions)
        teacher.getEducations().clear();

        // Add new education entries
        for (TeacherEducationDTO dto : educationDTOs) {
            TeacherEducation education = new TeacherEducation();
            education.setTeacher(teacher);
            education.setDegree(dto.getDegree());
            education.setInstitution(dto.getInstitution());
            education.setPassingYear(dto.getPassingYear());
            education.setGrade(dto.getGrade());

            teacher.getEducations().add(education);
        }
        log.info("Added {} educations to teacher collection", educationDTOs.size());
    }

    private void updateTeacherAvailabilities(Teacher teacher, List<TeacherAvailabilityDTO> availabilityDTOs) {
        if (availabilityDTOs == null || availabilityDTOs.isEmpty()) {
            throw new IllegalArgumentException("At least one availability slot is required");
        }

        if (availabilityDTOs.size() > 3) {
            throw new IllegalArgumentException("Maximum 3 availability slots allowed");
        }

        // First, clear existing availabilities
        teacher.getAvailabilities().clear();

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

            teacher.getAvailabilities().add(availability);
        }
        log.info("Added {} availabilities to teacher collection", availabilityDTOs.size());
    }

    @Transactional(readOnly = true)
    public List<TeacherAvailabilityDTO> getTeacherAvailability(String email) {
        Teacher teacher = findByEmail(email);
        if (teacher == null) {
            throw new ResourceNotFoundException("Teacher", "email", email);
        }

        if (teacher.getAvailabilities() == null) {
            return Collections.emptyList();
        }

        return teacher.getAvailabilities().stream()
                .map(av -> {
                    TeacherAvailabilityDTO dto = new TeacherAvailabilityDTO();
                    dto.setId(av.getId());
                    dto.setStartTime(av.getStartTime());
                    dto.setEndTime(av.getEndTime());
                    dto.setAvailableTimeForSlot(av.getAvailableTimeForSlot());
                    dto.setMonday(av.getMonday());
                    dto.setTuesday(av.getTuesday());
                    dto.setWednesday(av.getWednesday());
                    dto.setThursday(av.getThursday());
                    dto.setFriday(av.getFriday());
                    dto.setSaturday(av.getSaturday());
                    dto.setSunday(av.getSunday());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TeacherDashboardStatsDTO getTeacherDashboardStats(String email) {
        Teacher teacher = findByEmail(email);
        if (teacher == null) {
            throw new ResourceNotFoundException("Teacher", "email", email);
        }

        return TeacherDashboardStatsDTO.builder()
                .fullName(teacher.getFullName())
                .sessionsDelivered(0)
                .rating(5.0)
                .subjectsCount(teacher.getSubjects() != null ? teacher.getSubjects().size() : 0)
                // Add logic for profile completeness if needed, for now defaulting or calculating simple one
                .profileComplete(true) 
                .build();
    }

    @Transactional(readOnly = true)
    public TeacherPersonalInfoDTO getTeacherPersonalInfo(String email) {
        Teacher teacher = findByEmail(email);
        if (teacher == null) {
            throw new ResourceNotFoundException("Teacher", "email", email);
        }

        TeacherPersonalInfoDTO dto = new TeacherPersonalInfoDTO();
        dto.setId(teacher.getId());
        dto.setFullName(teacher.getFullName());
        // dto.setProfilePhoto(teacher.getProfilePhoto()); // Accessor needed if field exists
        dto.setExperience(teacher.getExperience());
        dto.setAboutMe(teacher.getAboutMe());
        dto.setCity(teacher.getCity());
        dto.setQualification(teacher.getQualifications()); // Note: entity has 'qualifications' string, DTO usually expects this
        dto.setGender(teacher.getGender());
        dto.setPhoneNumber(teacher.getPhoneNumber());
        dto.setWhatsappNumber(teacher.getWhatsappNumber());
        dto.setHasVehicle(teacher.getHasVehicle());
        dto.setAddress(teacher.getAddress());
        dto.setPin(teacher.getPin());
        dto.setState(teacher.getState());
        dto.setCountry(teacher.getCountry());
        
        if (teacher.getPreferredAreas() != null) {
             dto.setPreferredAreas(teacher.getPreferredAreas().stream()
                     .map(TeacherPreferredAreaMapping::getArea)
                     .collect(Collectors.toList()));
        }
        
        dto.setMode(teacher.getMode());
        dto.setExpectedFee(teacher.getExpectedFeePerHour());
        dto.setEmail(teacher.getEmail());
        return dto;
    }

    @Transactional(readOnly = true)
    public List<TeacherEducationDTO> getTeacherEducation(String email) {
        Teacher teacher = findByEmail(email);
        if (teacher == null) {
            throw new ResourceNotFoundException("Teacher", "email", email);
        }

        if (teacher.getEducations() == null) {
            return Collections.emptyList();
        }

        return teacher.getEducations().stream()
                .map(TeacherEducationConverter::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TeacherSubjectsDTO getTeacherSubjects(String email) {
        Teacher teacher = findByEmail(email);
        if (teacher == null) {
            throw new ResourceNotFoundException("Teacher", "email", email);
        }

        TeacherSubjectsDTO dto = new TeacherSubjectsDTO();
        
        // Populate using existing logic logic re-used from mapToProfileDTO or abstracted
        // For brevity, duplicating logic or ideally extracting to helper
        // Using same logic as mapToProfileDTO for subjects
        
        // 1. Subjects
        Map<String, List<String>> subjectsMap = new HashMap<>();
        if (teacher.getSubjects() != null) {
            for (TeacherSubjectMapping mapping : teacher.getSubjects()) {
                String className = String.valueOf(mapping.getSubjectClass().getClassId());
                String subjectName = mapping.getSubjectClass().getSubjectName();
                subjectsMap.computeIfAbsent(className, k -> new ArrayList<>()).add(subjectName);
            }
        }
        dto.setSubjects(subjectsMap);

        // 2. Extra Subjects
        Map<String, List<String>> extraSubjectsMap = new HashMap<>();
        if (teacher.getExtraSubjects() != null) {
            for (TeacherExtraSubjectMapping mapping : teacher.getExtraSubjects()) {
                // Grouping by "all" or specific category if needed. Previous DTO used "all"
                extraSubjectsMap.computeIfAbsent("all", k -> new ArrayList<>()).add(mapping.getExtraSubject().getExtraSubjectName());
            }
        }
        dto.setExtraSubjects(extraSubjectsMap);
        
        // 3. Raw IDs
        if (teacher.getSubjects() != null) {
            dto.setRawSubjectIds(teacher.getSubjects().stream()
                .map(mapping -> mapping.getSubjectClass().getId())
                .collect(Collectors.toSet()));
        }
        
        if (teacher.getExtraSubjects() != null) {
            dto.setRawExtraSubjectIds(teacher.getExtraSubjects().stream()
                .map(mapping -> mapping.getExtraSubject().getId())
                .collect(Collectors.toSet()));
        }

        // 4. Boards (Derived)
        Map<String, List<String>> boardsMap = new HashMap<>();
        // Logic to extract boards from subjects if available in entity graph
        // Assuming boards are derived from subject class or similar
        // For now leaving empty or simple logic as per existing mapToProfileDTO if any
        dto.setBoards(boardsMap);

        return dto;
    }

    @Transactional
    public TeacherAvailabilityDTO updateTeacherAvailabilitySlot(String email, Long availabilityId, TeacherAvailabilityDTO dto) {
        Teacher teacher = findByEmail(email);
        if (teacher == null) {
            throw new ResourceNotFoundException("Teacher", "email", email);
        }

        TeacherAvailability availability = teacherAvailabilityRepository.findById(availabilityId)
                .orElseThrow(() -> new ResourceNotFoundException("Availability", "id", availabilityId));

        // Security check: ensure availability belongs to the requesting teacher
        if (!availability.getTeacher().getId().equals(teacher.getId())) {
             throw new IllegalArgumentException("Availability slot does not belong to this teacher");
        }
        
        if (dto.getStartTime() >= dto.getEndTime()) {
            throw new IllegalArgumentException("End time must be after start time");
        }

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
        
        // Recalculate total availability (triggered by @PreUpdate in entity, but good to be explicit if needed, 
        // though JPA entity methods handle it)
        
        TeacherAvailability saved = teacherAvailabilityRepository.save(availability);
        
        // Return updated DTO
        dto.setId(saved.getId());
        return dto;
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
            throw new IllegalArgumentException(
                    "Must accept the latest Privacy Policy version: " + latestPrivacyPolicy.getVersion());
        }
        DocumentResponseDto ppDocDto = documentService.getByVersion(Document.DocumentType.PRIVACY_POLICY,
                agreementDTO.getPrivacyPolicyVersion());
        agreement.setPrivacyPolicy(repo
                .findByTypeAndVersion(Document.DocumentType.PRIVACY_POLICY, agreementDTO.getPrivacyPolicyVersion())
                .orElseThrow(() -> new IllegalStateException("Privacy Policy document not found after verification")));
        agreement.setPrivacyPolicyAcceptedAt(LocalDateTime.now());
        log.debug("Privacy Policy {} accepted at {}", ppDocDto.getVersion(), agreement.getPrivacyPolicyAcceptedAt());

        // 4. Verify and set Terms of Use
        DocumentResponseDto latestTerms = documentService.getLatest(Document.DocumentType.TERMS_OF_USE);
        if (!latestTerms.getVersion().equals(agreementDTO.getTermsOfUseVersion())) {
            log.error("Invalid Terms of Use version provided: {}, latest version: {}",
                    agreementDTO.getTermsOfUseVersion(), latestTerms.getVersion());
            throw new IllegalArgumentException(
                    "Must accept the latest Terms of Use version: " + latestTerms.getVersion());
        }
        DocumentResponseDto touDocDto = documentService.getByVersion(Document.DocumentType.TERMS_OF_USE,
                agreementDTO.getTermsOfUseVersion());
        agreement.setTermsOfUse(repo
                .findByTypeAndVersion(Document.DocumentType.TERMS_OF_USE, agreementDTO.getTermsOfUseVersion())
                .orElseThrow(() -> new IllegalStateException("Terms of Use document not found after verification")));
        agreement.setTermsOfUseAcceptedAt(LocalDateTime.now());
        log.debug("Terms of Use {} accepted at {}", touDocDto.getVersion(), agreement.getTermsOfUseAcceptedAt());

        // 5. Verify and set Teacher Agreement
        DocumentResponseDto latestTeacherAgreement = documentService.getLatest(Document.DocumentType.TEACHER_AGREEMENT);
        if (!latestTeacherAgreement.getVersion().equals(agreementDTO.getTeacherAgreementVersion())) {
            log.error("Invalid Teacher Agreement version provided: {}, latest version: {}",
                    agreementDTO.getTeacherAgreementVersion(), latestTeacherAgreement.getVersion());
            throw new IllegalArgumentException(
                    "Must accept the latest Teacher Agreement version: " + latestTeacherAgreement.getVersion());
        }
        DocumentResponseDto taDocDto = documentService.getByVersion(Document.DocumentType.TEACHER_AGREEMENT,
                agreementDTO.getTeacherAgreementVersion());
        agreement.setTeacherAgreement(repo
                .findByTypeAndVersion(Document.DocumentType.TEACHER_AGREEMENT,
                        agreementDTO.getTeacherAgreementVersion())
                .orElseThrow(
                        () -> new IllegalStateException("Teacher Agreement document not found after verification")));
        agreement.setTeacherAgreementAcceptedAt(LocalDateTime.now());
        log.debug("Teacher Agreement {} accepted at {}", taDocDto.getVersion(),
                agreement.getTeacherAgreementAcceptedAt());

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

    /**
     * CRITICAL SECURITY: Validate password complexity
     * Requirements:
     * - Minimum 8 characters
     * - At least 1 uppercase letter
     * - At least 1 lowercase letter
     * - At least 1 digit
     * - At least 1 special character (@$!%*?&#)
     */
    private void validatePasswordComplexity(String password) {
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        String specialChars = "@$!%*?&#";

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c))
                hasUpper = true;
            else if (Character.isLowerCase(c))
                hasLower = true;
            else if (Character.isDigit(c))
                hasDigit = true;
            else if (specialChars.indexOf(c) >= 0)
                hasSpecial = true;
        }

        if (!hasUpper) {
            throw new IllegalArgumentException("Password must contain at least one uppercase letter");
        }
        if (!hasLower) {
            throw new IllegalArgumentException("Password must contain at least one lowercase letter");
        }
        if (!hasDigit) {
            throw new IllegalArgumentException("Password must contain at least one digit");
        }
        if (!hasSpecial) {
            throw new IllegalArgumentException("Password must contain at least one special character (@$!%*?&#)");
        }

        // Check for common weak passwords
        String lowerPassword = password.toLowerCase();
        String[] weakPasswords = { "password", "12345678", "qwerty", "admin123", "welcome1" };
        for (String weak : weakPasswords) {
            if (lowerPassword.contains(weak)) {
                throw new IllegalArgumentException("Password is too common. Please choose a stronger password");
            }
        }

        log.debug("Password complexity validation passed");
    }

    private void updateSubjectMappings(Teacher teacher, Set<Long> subjectIds) {
        log.info("Updating subject mappings for teacher: {}", teacher.getId());

        // Clear existing mappings
        teacher.getSubjects().clear();

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
                teacher.getSubjects().add(mapping);
            });
            addedCount++;
        }
        log.info("Added {} subject mappings to teacher collection", addedCount);
    }

    private void updateExtraSubjectMappings(Teacher teacher, Set<Long> extraSubjectIds) {
        log.info("Updating extra subject mappings for teacher: {}", teacher.getId());

        // Clear existing mappings
        teacher.getExtraSubjects().clear();

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
                teacher.getExtraSubjects().add(mapping);
            });
            addedCount++;
        }
        log.info("Added {} extra subject mappings to teacher collection", addedCount);
    }

    private void updatePreferredAreas(Teacher teacher, Set<String> preferredAreasSet) {
        log.info("Updating preferred areas for teacher: {}", teacher.getId());

        // Clear existing area mappings
        teacher.getPreferredAreas().clear();

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
                teacher.getPreferredAreas().add(mapping);
                addedCount++;
            }
        }
        log.info("Added {} preferred area mappings to teacher collection", addedCount);
    }

    @Transactional(timeout = 30)
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

    @Transactional(readOnly = true, timeout = 30)
    public List<TeacherEducationDTO> getTeacherEducation(Long teacherId) {
        log.info("Fetching education details for teacher: {}", teacherId);

        // Verify teacher exists
        if (!teacherRepository.existsById(teacherId)) {
            throw new ResourceNotFoundException("Teacher", "id", teacherId);
        }

        // Get education records sorted by passing year
        List<TeacherEducation> educationRecords = teacherEducationRepository
                .findByTeacherIdOrderByPassingYearDesc(teacherId);

        return TeacherEducationConverter.toDtoList(educationRecords);
    }

    @Transactional(timeout = 30)
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

    @Transactional(timeout = 30)
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
    @Transactional(readOnly = true)
    public TeacherProfileDTO getTeacherProfileBySlug(String slug) {
        Teacher teacher = teacherRepository.findBySlug(slug)
            .orElseThrow(() -> new ResourceNotFoundException("Teacher", "slug", slug));
            
        return mapToProfileDTO(teacher);
    }

    private TeacherProfileDTO mapToProfileDTO(Teacher teacher) {
        TeacherProfileDTO dto = new TeacherProfileDTO();
        dto.setId(teacher.getId());
        dto.setSlug(teacher.getSlug());
        dto.setFullName(teacher.getFullName());
        dto.setExperience(teacher.getExperience());
        dto.setAboutMe(teacher.getAboutMe());
        dto.setCity(teacher.getCity());
        dto.setQualification(teacher.getQualifications());
        dto.setGender(teacher.getGender());
        dto.setPhoneNumber(teacher.getPhoneNumber());
        dto.setWhatsappNumber(teacher.getWhatsappNumber());
        dto.setHasVehicle(teacher.getHasVehicle());
        dto.setAddress(teacher.getAddress());
        dto.setPin(teacher.getPin());
        dto.setState(teacher.getState());
        dto.setCountry(teacher.getCountry());
        dto.setMode(teacher.getMode());
        dto.setExpectedFee(teacher.getExpectedFeePerHour());
        dto.setSessionsDelivered(10); // Mock data for now
        dto.setRating(4.8); // Mock data for now
        
        // Map subjects
        Map<String, List<String>> subjectsMap = new HashMap<>();
        if (teacher.getSubjects() != null) {
            for (TeacherSubjectMapping mapping : teacher.getSubjects()) {
                String classId = String.valueOf(mapping.getSubjectClass().getClassId());
                subjectsMap.computeIfAbsent(classId, k -> new ArrayList<>())
                          .add(mapping.getSubjectClass().getSubjectName());
            }
        }
        dto.setSubjects(subjectsMap);
        
        // Map extra subjects
        Map<String, List<String>> extraSubjectsMap = new HashMap<>();
        if (teacher.getExtraSubjects() != null && !teacher.getExtraSubjects().isEmpty()) {
            List<String> extras = teacher.getExtraSubjects().stream()
                .map(mapping -> mapping.getExtraSubject().getExtraSubjectName())
                .collect(Collectors.toList());
            extraSubjectsMap.put("all", extras);
        }
        dto.setExtraSubjects(extraSubjectsMap);

        // Populate Raw IDs for easier frontend management
        if (teacher.getSubjects() != null) {
            dto.setRawSubjectIds(teacher.getSubjects().stream()
                .map(m -> m.getSubjectClass().getId())
                .collect(Collectors.toSet()));
        }
        if (teacher.getExtraSubjects() != null) {
            dto.setRawExtraSubjectIds(teacher.getExtraSubjects().stream()
                .map(m -> m.getExtraSubject().getId())
                .collect(Collectors.toSet()));
        }
        
        // Map boards - Placeholder as distinct board entity doesn't seem to be linked directly in the viewed code
        // Utilizing a simple static list for now or derived if logic allows
        dto.setBoards(Map.of("all", List.of("CBSE", "State Board"))); 
        
        // Map availability
        if (teacher.getAvailabilities() != null) {
            dto.setAvailability(teacher.getAvailabilities().stream()
                .map(av -> {
                    TeacherAvailabilityDTO avDto = new TeacherAvailabilityDTO();
                    avDto.setId(av.getId());
                    avDto.setStartTime(av.getStartTime());
                    avDto.setEndTime(av.getEndTime());
                    avDto.setAvailableTimeForSlot(av.getAvailableTimeForSlot());
                    avDto.setMonday(av.getMonday());
                    avDto.setTuesday(av.getTuesday());
                    avDto.setWednesday(av.getWednesday());
                    avDto.setThursday(av.getThursday());
                    avDto.setFriday(av.getFriday());
                    avDto.setSaturday(av.getSaturday());
                    avDto.setSunday(av.getSunday());
                    return avDto;
                })
                .collect(Collectors.toList()));
        }

        // Map education
        if (teacher.getEducations() != null) {
            dto.setEducation(teacher.getEducations().stream()
                .map(TeacherEducationConverter::toDto)
                .collect(Collectors.toList()));
        }
        
        // Map preferred areas
        if (teacher.getPreferredAreas() != null) {
            dto.setPreferredAreas(teacher.getPreferredAreas().stream()
                .map(TeacherPreferredAreaMapping::getArea)
                .collect(Collectors.toList()));
        }
        
        // Map other details
        dto.setMode(teacher.getMode());
        dto.setExpectedFee(teacher.getExpectedFeePerHour());
        
        return dto;
    }

    @Transactional(timeout = 300) // Increase timeout for batch operation
    public int backfillSlugs() {
        log.info("Starting slug backfill for existing teachers...");
        List<Teacher> teachers = teacherRepository.findAll();
        int count = 0;
        
        for (Teacher teacher : teachers) {
            if (teacher.getSlug() == null) {
                try {
                    // Ensure subjects are loaded (fetch join equivalent or lazy load trigger)
                    // If slug generation fails for one, continue for others
                    teacher.setSlug(slugGeneratorService.generateSlug(teacher));
                    teacherRepository.save(teacher);
                    count++;
                } catch (Exception e) {
                    log.error("Failed to generate slug for teacher {}: {}", teacher.getId(), e.getMessage());
                }
            }
        }
        
        log.info("Completed slug backfill. Updated {} teachers.", count);
        return count;
    }

    @Transactional(readOnly = true)
    public Teacher findByEmail(String email) {
        return teacherRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public TeacherProfileDTO getMyProfile(String email) {
        Teacher teacher = findByEmail(email);
        if (teacher == null) {
            throw new ResourceNotFoundException("Teacher", "email", email);
        }
        return mapToProfileDTO(teacher);
    }

    @Transactional(timeout = 30)
    public TeacherProfileDTO updateProfile(String email, TeacherUpdateDTO dto) {
        Teacher teacher = findByEmail(email);
        if (teacher == null) {
            throw new ResourceNotFoundException("Teacher", "email", email);
        }

        // Update fields if provided in DTO
        if (dto.getFullName() != null) {
            teacher.setFullName(htmlSanitizer.sanitizeNotEmpty(dto.getFullName()));
        }
        // Removed direct email update. Email update must go through initiateEmailUpdate flow.
        if (dto.getPhoneNumber() != null) {
            teacher.setPhoneNumber(dto.getPhoneNumber());
        }
        if (dto.getWhatsappNumber() != null) {
            teacher.setWhatsappNumber(dto.getWhatsappNumber());
        }
        if (dto.getAboutMe() != null) {
            teacher.setAboutMe(htmlSanitizer.sanitize(dto.getAboutMe()));
        }
        if (dto.getExperience() != null) {
            teacher.setExperience(dto.getExperience());
        }
        if (dto.getGender() != null) {
            teacher.setGender(validateGender(dto.getGender()));
        }
        if (dto.getHasVehicle() != null) {
            teacher.setHasVehicle(dto.getHasVehicle());
        }
        if (dto.getAddress() != null) {
            teacher.setAddress(htmlSanitizer.sanitizeNotEmpty(dto.getAddress()));
        }
        if (dto.getCity() != null) {
            teacher.setCity(dto.getCity());
        }
        if (dto.getPin() != null) {
            teacher.setPin(dto.getPin());
        }
        if (dto.getState() != null) {
            teacher.setState(dto.getState());
        }
        if (dto.getCountry() != null) {
            teacher.setCountry(dto.getCountry());
        }
        if (dto.getMode() != null) {
            teacher.setMode(dto.getMode());
        }
        if (dto.getExpectedFeePerHour() != null) {
            teacher.setExpectedFeePerHour(dto.getExpectedFeePerHour());
        }
        if (dto.getQualification() != null) {
            teacher.setQualifications(htmlSanitizer.sanitize(dto.getQualification()));
        }

        // Complex mappings
        if (dto.getSubjectIds() != null) {
            updateSubjectMappings(teacher, dto.getSubjectIds());
        }
        if (dto.getExtraSubjectIds() != null) {
            updateExtraSubjectMappings(teacher, dto.getExtraSubjectIds());
        }
        if (dto.getEducations() != null) {
            updateTeacherEducations(teacher, dto.getEducations());
        }
        if (dto.getAvailabilities() != null) {
            updateTeacherAvailabilities(teacher, dto.getAvailabilities());
        }
        if (dto.getPreferredAreas() != null) {
            updatePreferredAreas(teacher, new java.util.HashSet<>(dto.getPreferredAreas()));
        }

        teacher.setUpdatedAt(LocalDateTime.now());
        
        // Save and return mapped DTO
        return mapToProfileDTO(teacherRepository.save(teacher));
    }

    @Transactional(timeout = 30)
    public void initiatePasswordReset(String email) {
        Teacher teacher = teacherRepository.findByEmail(email);
        if (teacher == null) {
            // Log but don't reveal to avoid email harvesting
            log.warn("Password reset requested for non-existent email: {}", email);
            return;
        }

        // Generate reset OTP
        String otp = String.format("%06d", random.nextInt(1000000));
        teacher.setEmailOtp(otp);
        teacher.setEmailOtpGeneratedAt(LocalDateTime.now());
        teacher.setOtpAttempts(0); // Reset attempts
        teacherRepository.save(teacher);

        emailService.sendPasswordResetOtp(email, otp);
        log.info("Initiated password reset for email: {}", email);
    }

    @Transactional(timeout = 30)
    public void resetPassword(PasswordResetVerifyRequest req) {
        Teacher teacher = teacherRepository.findByEmail(req.getEmail());
        if (teacher == null) {
            throw new IllegalArgumentException("Invalid request");
        }

        // Verify OTP (Check expiry etc same as registration)
        if (teacher.getEmailOtp() == null || teacher.getEmailOtpGeneratedAt() == null) {
            throw new IllegalArgumentException("No reset request found");
        }

        if (teacher.getEmailOtpGeneratedAt().plusMinutes(5).isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("OTP has expired");
        }

        if (!teacher.getEmailOtp().equals(req.getOtp())) {
            throw new IllegalArgumentException("Invalid OTP");
        }

        // Validate password complexity
        validatePasswordComplexity(req.getNewPassword());

        // Update password
        teacher.setPassword(passwordEncoder.encode(req.getNewPassword()));
        teacher.setEmailOtp(null); // Clear OTP
        teacher.setEmailOtpGeneratedAt(null);
        teacher.setUpdatedAt(LocalDateTime.now());
        teacherRepository.save(teacher);

        log.info("Password successfully reset for email: {}", req.getEmail());
    }

    @Transactional(timeout = 30)
    public void updateTeacherSubjects(String email, Set<Long> subjectIds) {
        Teacher teacher = findByEmail(email);
        if (teacher == null) throw new ResourceNotFoundException("Teacher", "email", email);
        updateSubjectMappings(teacher, subjectIds);
        teacherRepository.save(teacher);
    }

    @Transactional(timeout = 30)
    public void updateTeacherExtraSubjects(String email, Set<Long> extraSubjectIds) {
        Teacher teacher = findByEmail(email);
        if (teacher == null) throw new ResourceNotFoundException("Teacher", "email", email);
        updateExtraSubjectMappings(teacher, extraSubjectIds);
        teacherRepository.save(teacher);
    }

    @Transactional(timeout = 30)
    public void updateTeacherAvailabilities(String email, List<TeacherAvailabilityDTO> availabilityDTOs) {
        Teacher teacher = findByEmail(email);
        if (teacher == null) throw new ResourceNotFoundException("Teacher", "email", email);
        updateTeacherAvailabilities(teacher, availabilityDTOs);
        teacherRepository.save(teacher);
    }

    @Transactional(timeout = 30)
    public void updateTeacherEducations(String email, List<TeacherEducationDTO> educationDTOs) {
        Teacher teacher = findByEmail(email);
        if (teacher == null) throw new ResourceNotFoundException("Teacher", "email", email);
        updateTeacherEducations(teacher, educationDTOs);
        teacherRepository.save(teacher);
    }

    @Transactional(timeout = 30)
    public void initiateEmailUpdate(String currentEmail, String newEmail) {
        if (newEmail == null || newEmail.isBlank()) {
            throw new IllegalArgumentException("New email is required");
        }
        if (currentEmail.equalsIgnoreCase(newEmail)) {
            throw new IllegalArgumentException("New email must be different from current email");
        }

        Teacher teacher = findByEmail(currentEmail);
        if (teacher == null) {
            throw new ResourceNotFoundException("Teacher", "email", currentEmail);
        }

        // Check if new email is already taken
        Teacher existing = teacherRepository.findByEmail(newEmail);
        if (existing != null) {
            throw new IllegalArgumentException("Email already in use by another account");
        }

        // Generate OTP
        String otp = String.format("%06d", random.nextInt(1000000));
        
        // Update teacher record
        teacher.setPendingNewEmail(newEmail);
        teacher.setEmailOtp(otp);
        teacher.setEmailOtpGeneratedAt(LocalDateTime.now());
        teacher.setOtpAttempts(0);
        teacherRepository.save(teacher);

        // Send OTP to NEW email
        emailService.sendOtp(newEmail, otp);
        log.info("Initiated email update for teacher {}. OTP sent to {}", teacher.getId(), newEmail);
    }

    @Transactional(timeout = 30)
    public void verifyEmailUpdate(String currentEmail, String otp) {
        Teacher teacher = findByEmail(currentEmail);
        if (teacher == null) {
            throw new ResourceNotFoundException("Teacher", "email", currentEmail);
        }

        if (teacher.getPendingNewEmail() == null) {
            throw new IllegalArgumentException("No pending email update found");
        }

        // Validate OTP (Same logic as other OTP checks)
        if (teacher.getEmailOtp() == null || teacher.getEmailOtpGeneratedAt() == null) {
            throw new IllegalArgumentException("OTP expired or invalid");
        }
        
        if (teacher.getEmailOtpGeneratedAt().plusMinutes(5).isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("OTP has expired");
        }

        if (!teacher.getEmailOtp().equals(otp)) {
            teacher.setOtpAttempts((teacher.getOtpAttempts() == null ? 0 : teacher.getOtpAttempts()) + 1);
            teacherRepository.save(teacher);
            throw new IllegalArgumentException("Invalid OTP");
        }

        // Update Email
        String oldEmail = teacher.getEmail();
        teacher.setEmail(teacher.getPendingNewEmail());
        
        // Clear pending fields
        teacher.setPendingNewEmail(null);
        teacher.setEmailOtp(null);
        teacher.setEmailOtpGeneratedAt(null);
        teacher.setOtpAttempts(0);
        
        teacherRepository.save(teacher);
        log.info("Successfully updated email for teacher {} from {} to {}", teacher.getId(), oldEmail, teacher.getEmail());
    }
}
