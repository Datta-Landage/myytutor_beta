package com.myytutor.api;

import com.myytutor.dto.TeacherEmailVerificationRequest;
import com.myytutor.dto.TeacherOtpVerificationRequest;
import com.myytutor.dto.TeacherProfileDTO;
import com.myytutor.dto.TeacherRegistrationRequest;
import com.myytutor.dto.TeacherDashboardStatsDTO;
import com.myytutor.dto.TeacherPersonalInfoDTO;
import com.myytutor.dto.TeacherSubjectsDTO;
import com.myytutor.dto.TeacherEducationDTO;
import com.myytutor.dto.ApiResponse;
import com.myytutor.entity.Teacher;
import com.myytutor.service.TeacherService;
import com.myytutor.util.IpAddressExtractor;

import java.util.List;
import java.util.Map;
import java.util.Set;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/teachers")
@Tag(name = "Teacher Registration", description = "APIs for teacher registration process")
@CrossOrigin(origins = "${app.cors.allowed-origins}", maxAge = 3600)
@RequiredArgsConstructor
@Validated
public class TeacherController {
    private static final Logger log = LoggerFactory.getLogger(TeacherController.class);
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private IpAddressExtractor ipExtractor;

    @Operation(summary = "Send OTP for email verification", description = "Sends a verification OTP to the provided email address")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OTP sent successfully", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid email format or email already verified", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @PostMapping(value = "/send-otp", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> sendVerificationEmail(
            @Valid @RequestBody TeacherEmailVerificationRequest request,
            HttpServletRequest httpRequest) {
        String ipAddress = ipExtractor.getClientIpAddress(httpRequest);
        log.info("Sending OTP for email verification: {} from IP: {}", request.getEmail(), ipAddress);
        teacherService.sendVerificationOtp(request, ipAddress);
        return ResponseEntity.ok(new ApiResponse("success", "Verification OTP sent to your email"));
    }

    @Operation(summary = "Verify OTP", description = "Verifies the OTP sent to teacher's email")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OTP verified successfully", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid OTP or OTP expired", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @PostMapping(value = "/verify-otp", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> verifyOtp(
            @Valid @RequestBody TeacherOtpVerificationRequest request) {
        log.info("Verifying OTP for email: {}", request.getEmail());
        teacherService.verifyOtp(request);
        return ResponseEntity.ok(new ApiResponse("success", "Email verified successfully"));
    }

    @Operation(summary = "Complete teacher registration", description = "Registers a new teacher with all required information including education, "
            +
            "subjects, availability and agreements")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Teacher registered successfully", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid registration data or email not verified", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> register(
            @Valid @RequestBody TeacherRegistrationRequest request) {
        log.info("Processing teacher registration for email: {}", request.getEmail());
        Teacher registeredTeacher = teacherService.registerTeacher(request);
        return ResponseEntity.ok(new ApiResponse("success",
                String.format("Teacher %s registered successfully!", registeredTeacher.getEmail())));
    }

    @Operation(summary = "Get teacher profile by slug", description = "Retrieves public teacher profile using SEO-friendly slug")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Profile retrieved successfully", content = @Content(schema = @Schema(implementation = TeacherProfileDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Teacher not found", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @GetMapping(value = "/tutor/{slug}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TeacherProfileDTO> getTeacherBySlug(@PathVariable String slug) {
        log.info("Fetching teacher profile for slug: {}", slug);
        TeacherProfileDTO profile = teacherService.getTeacherProfileBySlug(slug);
        return ResponseEntity.ok(profile);
    }

    @Operation(summary = "Get current teacher profile", description = "Retrieves the profile of the currently authenticated teacher")
    @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TeacherProfileDTO> getMyProfile() {
        String email = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("Fetching logged-in teacher profile for email: {}", email);
        TeacherProfileDTO profile = teacherService.getMyProfile(email);
        return ResponseEntity.ok(profile);
    }

    @Operation(summary = "Update teacher profile", description = "Updates the profile of the currently authenticated teacher")
    @PutMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TeacherProfileDTO> updateProfile(@RequestBody com.myytutor.dto.TeacherUpdateDTO request) {
        String email = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("Updating profile for teacher email: {}", email);
        TeacherProfileDTO updatedProfile = teacherService.updateProfile(email, request);
        return ResponseEntity.ok(updatedProfile);
    }

    @Operation(summary = "Initiate email update", description = "Sends OTP to the new email address")
    @PostMapping(value = "/email-update/initiate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> initiateEmailUpdate(@RequestBody com.myytutor.dto.EmailUpdateInitiateRequest request) {
        String email = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName();
        teacherService.initiateEmailUpdate(email, request.getNewEmail());
        return ResponseEntity.ok(Map.of("message", "OTP sent to new email address"));
    }

    @Operation(summary = "Verify email update", description = "Verifies OTP and updates email")
    @PostMapping(value = "/email-update/verify", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> verifyEmailUpdate(@RequestBody com.myytutor.dto.EmailUpdateVerifyRequest request) {
        String email = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName();
        teacherService.verifyEmailUpdate(email, request.getOtp());
        return ResponseEntity.ok(Map.of("message", "Email updated successfully"));
    }

    @Operation(summary = "Update teacher subjects", description = "Updates the subject mappings for the currently authenticated teacher")
    @PutMapping(value = "/subjects", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> updateSubjects(@RequestBody Set<Long> subjectIds) {
        String email = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName();
        teacherService.updateTeacherSubjects(email, subjectIds);
        return ResponseEntity.ok(new ApiResponse("success", "Subjects updated successfully"));
    }

    @Operation(summary = "Update teacher extra subjects", description = "Updates the extra subject mappings for the currently authenticated teacher")
    @PutMapping(value = "/extra-subjects", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> updateExtraSubjects(@RequestBody Set<Long> extraSubjectIds) {
        String email = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName();
        teacherService.updateTeacherExtraSubjects(email, extraSubjectIds);
        return ResponseEntity.ok(new ApiResponse("success", "Extra subjects updated successfully"));
    }

    @Operation(summary = "Update teacher availability", description = "Updates the availability slots for the currently authenticated teacher")
    @PutMapping(value = "/availability", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> updateAvailability(@RequestBody List<com.myytutor.dto.TeacherAvailabilityDTO> availabilityDTOs) {
        String email = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName();
        teacherService.updateTeacherAvailabilities(email, availabilityDTOs);
        return ResponseEntity.ok(new ApiResponse("success", "Availability updated successfully"));
    }

    @Operation(summary = "Get teacher availability", description = "Retrieves the availability slots for the currently authenticated teacher")
    @GetMapping(value = "/me/availability", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<com.myytutor.dto.TeacherAvailabilityDTO>> getMyAvailability() {
        String email = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName();
        List<com.myytutor.dto.TeacherAvailabilityDTO> availability = teacherService.getTeacherAvailability(email);
        return ResponseEntity.ok(availability);
    }

    @Operation(summary = "Update specific availability slot", description = "Updates a specific availability slot by ID")
    @PutMapping(value = "/availability/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<com.myytutor.dto.TeacherAvailabilityDTO> updateAvailabilitySlot(
            @PathVariable Long id,
            @RequestBody com.myytutor.dto.TeacherAvailabilityDTO dto) {
        String email = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName();
        com.myytutor.dto.TeacherAvailabilityDTO updated = teacherService.updateTeacherAvailabilitySlot(email, id, dto);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Get teacher dashboard stats", description = "Retrieves lightweight stats for the dashboard home")
    @GetMapping(value = "/me/dashboard-stats", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TeacherDashboardStatsDTO> getDashboardStats() {
        String email = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(teacherService.getTeacherDashboardStats(email));
    }

    @Operation(summary = "Get teacher personal info", description = "Retrieves personal information for the profile page")
    @GetMapping(value = "/me/personal-info", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TeacherPersonalInfoDTO> getPersonalInfo() {
        String email = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(teacherService.getTeacherPersonalInfo(email));
    }

    @Operation(summary = "Get teacher education", description = "Retrieves education records")
    @GetMapping(value = "/me/education", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TeacherEducationDTO>> getMyEducation() {
        String email = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(teacherService.getTeacherEducation(email));
    }

    @Operation(summary = "Get teacher subjects", description = "Retrieves subject mappings")
    @GetMapping(value = "/me/subjects", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TeacherSubjectsDTO> getMySubjects() {
        String email = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(teacherService.getTeacherSubjects(email));
    }

    @Operation(summary = "Update teacher education", description = "Updates the education records for the currently authenticated teacher")
    @PutMapping(value = "/education", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> updateEducation(@RequestBody List<com.myytutor.dto.TeacherEducationDTO> educationDTOs) {
        String email = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName();
        teacherService.updateTeacherEducations(email, educationDTOs);
        return ResponseEntity.ok(new ApiResponse("success", "Education records updated successfully"));
    }

    @Operation(summary = "Backfill slugs for existing teachers", description = "Generates slugs for all teachers who don't have one")
    @PostMapping(value = "/slug-backfill", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> backfillSlugs() {
        int count = teacherService.backfillSlugs();
        return ResponseEntity.ok(new ApiResponse("success", "Backfilled slugs for " + count + " teachers"));
    }

}
