package com.myytutor.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class TeacherRegistrationRequest {
    @Email(message = "Invalid email format")
    private String email;

    private Boolean emailVerified;
    private String emailOtp;
    private LocalDateTime emailOtpGeneratedAt;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String phoneNumber;

    @Pattern(regexp = "^[0-9]{10}$", message = "WhatsApp number must be 10 digits")
    private String whatsappNumber;

    @NotBlank(message = "Gender is required")
    private String gender;

    @Size(max = 500, message = "Educational qualifications cannot exceed 500 characters")
    private String qualifications;

    @Size(max = 500, message = "Certifications cannot exceed 500 characters")
    private String certifications;

    @NotNull(message = "Experience is required")
    @Min(value = 0, message = "Experience cannot be negative")
    private Integer experience;

    @NotBlank(message = "Vehicle information is required")
    private String hasVehicle;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "PIN code is required")
    @Pattern(regexp = "^[0-9]{6}$", message = "PIN code must be 6 digits")
    private String pin;

    @NotBlank(message = "Address is required")
    private String address;

    @Size(max = 2000)
    private String aboutMe;

    @NotBlank(message = "Teaching mode is required")
    private String mode;

    @NotEmpty(message = "At least one preferred area is required")
    private Set<String> preferredAreas;

    @NotNull(message = "Expected fee per hour is required")
    @Min(value = 0, message = "Fee cannot be negative")
    private Integer expectedFeePerHour;

    @NotEmpty(message = "At least one additional subject is required")
    private Set<Long> additionalSubjects;

    @NotEmpty(message = "At least one main subject is required")
    private Set<Long> subjectIds;

    @NotEmpty(message = "At least one availability slot is required")
    @Size(min = 1, max = 3, message = "Teacher must have between 1 and 3 availability slots")
    @Valid
    private List<TeacherAvailabilityDTO> availabilities;

    @NotEmpty(message = "At least one education entry is required")
    @Size(min = 1, max = 3, message = "Teacher must have between 1 and 3 education entries")
    @Valid
    private List<TeacherEducationDTO> educations;

    @NotNull(message = "Teacher agreement is required")
    @Valid
    private TeacherAgreementDTO teacherAgreement;

    public TeacherAgreementDTO getTeacherAgreement() {
        return teacherAgreement;
    }

    public void setTeacherAgreement(TeacherAgreementDTO teacherAgreement) {
        this.teacherAgreement = teacherAgreement;
    }

    // Getters and Setters
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getEmailOtp() {
        return emailOtp;
    }

    public void setEmailOtp(String emailOtp) {
        this.emailOtp = emailOtp;
    }

    public LocalDateTime getEmailOtpGeneratedAt() {
        return emailOtpGeneratedAt;
    }

    public void setEmailOtpGeneratedAt(LocalDateTime emailOtpGeneratedAt) {
        this.emailOtpGeneratedAt = emailOtpGeneratedAt;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWhatsappNumber() {
        return whatsappNumber;
    }

    public void setWhatsappNumber(String whatsappNumber) {
        this.whatsappNumber = whatsappNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getQualifications() {
        return qualifications;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    public String getCertifications() {
        return certifications;
    }

    public void setCertifications(String certifications) {
        this.certifications = certifications;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public String getHasVehicle() {
        return hasVehicle;
    }

    public void setHasVehicle(String hasVehicle) {
        this.hasVehicle = hasVehicle;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Set<String> getPreferredAreas() {
        return preferredAreas;
    }

    public void setPreferredAreas(Set<String> preferredAreas) {
        this.preferredAreas = preferredAreas;
    }

    public Integer getExpectedFeePerHour() {
        return expectedFeePerHour;
    }

    public void setExpectedFeePerHour(Integer expectedFeePerHour) {
        this.expectedFeePerHour = expectedFeePerHour;
    }

    public Set<Long> getAdditionalSubjects() {
        return additionalSubjects;
    }

    public void setAdditionalSubjects(Set<Long> additionalSubjects) {
        this.additionalSubjects = additionalSubjects;
    }

    public Set<Long> getSubjectIds() {
        return subjectIds;
    }

    public void setSubjectIds(Set<Long> subjectIds) {
        this.subjectIds = subjectIds;
    }

    public List<TeacherAvailabilityDTO> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(List<TeacherAvailabilityDTO> availabilities) {
        this.availabilities = availabilities;
    }

    public List<TeacherEducationDTO> getEducations() {
        return educations;
    }

    public void setEducations(List<TeacherEducationDTO> educations) {
        this.educations = educations;
    }
}

