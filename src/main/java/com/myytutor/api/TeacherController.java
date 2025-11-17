package com.myytutor.api;

import com.myytutor.dto.TeacherEmailVerificationRequest;
import com.myytutor.dto.TeacherOtpVerificationRequest;
import com.myytutor.dto.TeacherRegistrationRequest;
import com.myytutor.dto.ApiResponse;
import com.myytutor.entity.Teacher;
import com.myytutor.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

    @Operation(summary = "Send OTP for email verification",
            description = "Sends a verification OTP to the provided email address")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "OTP sent successfully",
            content = @Content(schema = @Schema(implementation = ApiResponse.class))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400",
            description = "Invalid email format or email already verified",
            content = @Content(schema = @Schema(implementation = ApiResponse.class))
        )
    })
    @PostMapping(value = "/send-otp", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> sendVerificationEmail(
            @Valid @RequestBody TeacherEmailVerificationRequest request) {
        log.info("Sending OTP for email verification: {}", request.getEmail());
        teacherService.sendVerificationOtp(request);
        return ResponseEntity.ok(new ApiResponse("success", "Verification OTP sent to your email"));
    }

    @Operation(summary = "Verify OTP",
            description = "Verifies the OTP sent to teacher's email")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "OTP verified successfully",
            content = @Content(schema = @Schema(implementation = ApiResponse.class))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400",
            description = "Invalid OTP or OTP expired",
            content = @Content(schema = @Schema(implementation = ApiResponse.class))
        )
    })
    @PostMapping(value = "/verify-otp", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> verifyOtp(
            @Valid @RequestBody TeacherOtpVerificationRequest request) {
        log.info("Verifying OTP for email: {}", request.getEmail());
        teacherService.verifyOtp(request);
        return ResponseEntity.ok(new ApiResponse("success", "Email verified successfully"));
    }

    @Operation(summary = "Complete teacher registration",
            description = "Registers a new teacher with all required information including education, " +
                    "subjects, availability and agreements")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Teacher registered successfully",
            content = @Content(schema = @Schema(implementation = ApiResponse.class))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400",
            description = "Invalid registration data or email not verified",
            content = @Content(schema = @Schema(implementation = ApiResponse.class))
        )
    })
    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> register(
            @Valid @RequestBody TeacherRegistrationRequest request) {
        log.info("Processing teacher registration for email: {}", request.getEmail());
        Teacher registeredTeacher = teacherService.registerTeacher(request);
        return ResponseEntity.ok(new ApiResponse("success", 
            String.format("Teacher %s registered successfully!", registeredTeacher.getEmail())));
    }


}
