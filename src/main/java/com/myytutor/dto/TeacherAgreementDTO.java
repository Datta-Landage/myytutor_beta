package com.myytutor.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public class TeacherAgreementDTO {
    @NotNull(message = "Privacy policy acceptance is required")
    private Boolean acceptedPrivacyPolicy;

    @NotBlank(message = "Privacy policy version is required")
    private String privacyPolicyVersion;

    private LocalDateTime privacyPolicyAcceptedAt;

    @NotNull(message = "Terms of use acceptance is required")
    private Boolean acceptedTermsOfUse;

    @NotBlank(message = "Terms of use version is required")
    private String termsOfUseVersion;

    private LocalDateTime termsOfUseAcceptedAt;

    @NotNull(message = "Teacher agreement acceptance is required")
    private Boolean acceptedTeacherAgreement;

    @NotBlank(message = "Teacher agreement version is required")
    private String teacherAgreementVersion;

    private LocalDateTime teacherAgreementAcceptedAt;

    // Getters and Setters
    public Boolean getAcceptedPrivacyPolicy() {
        return acceptedPrivacyPolicy;
    }

    public void setAcceptedPrivacyPolicy(Boolean acceptedPrivacyPolicy) {
        this.acceptedPrivacyPolicy = acceptedPrivacyPolicy;
    }

    public String getPrivacyPolicyVersion() {
        return privacyPolicyVersion;
    }

    public void setPrivacyPolicyVersion(String privacyPolicyVersion) {
        this.privacyPolicyVersion = privacyPolicyVersion;
    }

    public LocalDateTime getPrivacyPolicyAcceptedAt() {
        return privacyPolicyAcceptedAt;
    }

    public void setPrivacyPolicyAcceptedAt(LocalDateTime privacyPolicyAcceptedAt) {
        this.privacyPolicyAcceptedAt = privacyPolicyAcceptedAt;
    }

    public Boolean getAcceptedTermsOfUse() {
        return acceptedTermsOfUse;
    }

    public void setAcceptedTermsOfUse(Boolean acceptedTermsOfUse) {
        this.acceptedTermsOfUse = acceptedTermsOfUse;
    }

    public String getTermsOfUseVersion() {
        return termsOfUseVersion;
    }

    public void setTermsOfUseVersion(String termsOfUseVersion) {
        this.termsOfUseVersion = termsOfUseVersion;
    }

    public LocalDateTime getTermsOfUseAcceptedAt() {
        return termsOfUseAcceptedAt;
    }

    public void setTermsOfUseAcceptedAt(LocalDateTime termsOfUseAcceptedAt) {
        this.termsOfUseAcceptedAt = termsOfUseAcceptedAt;
    }

    public Boolean getAcceptedTeacherAgreement() {
        return acceptedTeacherAgreement;
    }

    public void setAcceptedTeacherAgreement(Boolean acceptedTeacherAgreement) {
        this.acceptedTeacherAgreement = acceptedTeacherAgreement;
    }

    public String getTeacherAgreementVersion() {
        return teacherAgreementVersion;
    }

    public void setTeacherAgreementVersion(String teacherAgreementVersion) {
        this.teacherAgreementVersion = teacherAgreementVersion;
    }

    public LocalDateTime getTeacherAgreementAcceptedAt() {
        return teacherAgreementAcceptedAt;
    }

    public void setTeacherAgreementAcceptedAt(LocalDateTime teacherAgreementAcceptedAt) {
        this.teacherAgreementAcceptedAt = teacherAgreementAcceptedAt;
    }
}