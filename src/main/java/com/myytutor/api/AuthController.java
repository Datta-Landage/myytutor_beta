package com.myytutor.api;

import com.myytutor.dto.ApiResponse;
import com.myytutor.dto.AuthResponse;
import com.myytutor.dto.LoginRequest;
import com.myytutor.entity.Teacher;
import com.myytutor.service.TeacherService;
import com.myytutor.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final TeacherService teacherService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        log.info("Login attempt for email: {}", request.getEmail());
        
        Teacher teacher = teacherService.findByEmail(request.getEmail());
        if (teacher == null) {
            return ResponseEntity.badRequest().body(new ApiResponse("error", "Invalid email or password"));
        }

        if (!passwordEncoder.matches(request.getPassword(), teacher.getPassword())) {
            return ResponseEntity.badRequest().body(new ApiResponse("error", "Invalid email or password"));
        }

        if (!teacher.getEmailVerified()) {
            return ResponseEntity.badRequest().body(new ApiResponse("error", "Email not verified. Please verify your email first."));
        }

        String token = jwtUtil.generateToken(teacher.getEmail());
        
        return ResponseEntity.ok(new AuthResponse(
                token, 
                teacher.getEmail(), 
                teacher.getFullName(), 
                "TEACHER"
        ));
    }

    @PostMapping("/forgot-password/initiate")
    public ResponseEntity<ApiResponse> initiateReset(@Valid @RequestBody com.myytutor.dto.PasswordResetRequest request) {
        teacherService.initiatePasswordReset(request.getEmail());
        return ResponseEntity.ok(new ApiResponse("success", "If email exists, a reset OTP has been sent."));
    }

    @PostMapping("/forgot-password/reset")
    public ResponseEntity<ApiResponse> verifyAndReset(@Valid @RequestBody com.myytutor.dto.PasswordResetVerifyRequest request) {
        try {
            teacherService.resetPassword(request);
            return ResponseEntity.ok(new ApiResponse("success", "Password has been reset successfully."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponse("error", e.getMessage()));
        }
    }
}
