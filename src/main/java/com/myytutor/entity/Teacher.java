package com.myytutor.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "teacher", uniqueConstraints = { @UniqueConstraint(columnNames = { "email" }) })
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String fullName;

    @Column(unique = true)
    private String email;

    @Column
    private String phoneNumber;

    @Column
    private String whatsappNumber;

    // Authentication and Security
    @Column
    private String password;

    // Email verification fields
    @Column(nullable = false)
    private Boolean emailVerified = false;

    @Column
    private String emailOtp;

    @Column
    private LocalDateTime emailOtpGeneratedAt;

    @Column
    private LocalDateTime emailVerifiedAt;

    // OTP brute force protection
    @Column
    private Integer otpAttempts = 0;

    @Column
    private LocalDateTime otpLockedUntil;

    // Basic information
    @Column(length = 2000)
    private String aboutMe;

    @Column
    private LocalDateTime dateOfBirth;

    @Column
    private Integer experience;

    @Column
    private String gender;

    // Location and Transport
    @Column
    private String hasVehicle;

    @Column
    private String address;

    @Column
    private String city;

    @Column
    private String pin;

    @Column
    private String state;

    @Column
    private String country;

    // Offline/Online/Both
    @Column
    private String mode;

    @Column
    private Integer expectedFeePerHour;

    // Preferred Areas mapping
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TeacherPreferredAreaMapping> preferredAreas = new HashSet<>();

    // Subject mappings
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TeacherSubjectMapping> subjects = new HashSet<>();

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TeacherExtraSubjectMapping> extraSubjects = new HashSet<>();

    // Teacher Availability - Minimum 1, Maximum 3
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TeacherAvailability> availabilities = new HashSet<>();

    // Teacher Education - Minimum 1, Maximum 3
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TeacherEducation> educations = new HashSet<>();

    // Documents and Verification
    @Column(length = 1000)
    private String qualifications;

    @Column(length = 1000)
    private String certifications;

    // Note: Document relationship removed - Documents are global legal documents
    // (privacy policy, terms, etc.)
    // not teacher-specific documents. If you need teacher-specific documents,
    // create a separate TeacherDocument entity.

    // Agreement relationship - Only ONE agreement per teacher (required at
    // registration)
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "teacher_agreement_id", unique = true)
    private TeacherAgreement agreement;

    // Audit fields
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Basic field getters/setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    // Authentication and Security getters/setters
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Email verification getters/setters
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

    public LocalDateTime getEmailVerifiedAt() {
        return emailVerifiedAt;
    }

    public void setEmailVerifiedAt(LocalDateTime emailVerifiedAt) {
        this.emailVerifiedAt = emailVerifiedAt;
    }

    public Integer getOtpAttempts() {
        return otpAttempts;
    }

    public void setOtpAttempts(Integer otpAttempts) {
        this.otpAttempts = otpAttempts;
    }

    public LocalDateTime getOtpLockedUntil() {
        return otpLockedUntil;
    }

    public void setOtpLockedUntil(LocalDateTime otpLockedUntil) {
        this.otpLockedUntil = otpLockedUntil;
    }

    // Phone verification getters/setters

    // Basic information getters/setters
    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public LocalDateTime getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    // Location and Transport getters/setters
    public String getHasVehicle() {
        return hasVehicle;
    }

    public void setHasVehicle(String hasVehicle) {
        this.hasVehicle = hasVehicle;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Integer getExpectedFeePerHour() {
        return expectedFeePerHour;
    }

    public void setExpectedFeePerHour(Integer expectedFeePerHour) {
        this.expectedFeePerHour = expectedFeePerHour;
    }

    public Set<TeacherPreferredAreaMapping> getPreferredAreas() {
        return preferredAreas;
    }

    public void setPreferredAreas(Set<TeacherPreferredAreaMapping> preferredAreas) {
        this.preferredAreas = preferredAreas;
    }

    // Subject mappings getters/setters
    public Set<TeacherSubjectMapping> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<TeacherSubjectMapping> subjects) {
        this.subjects = subjects;
    }

    public Set<TeacherExtraSubjectMapping> getExtraSubjects() {
        return extraSubjects;
    }

    public void setExtraSubjects(Set<TeacherExtraSubjectMapping> extraSubjects) {
        this.extraSubjects = extraSubjects;
    }

    // Teacher Availability getters/setters (Min: 1, Max: 3)
    public Set<TeacherAvailability> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(Set<TeacherAvailability> availabilities) {
        this.availabilities = availabilities;
    }

    // Teacher Education getters/setters (Min: 1, Max: 3)
    public Set<TeacherEducation> getEducations() {
        return educations;
    }

    public void setEducations(Set<TeacherEducation> educations) {
        this.educations = educations;
    }

    // Documents and Verification getters/setters
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

    // Agreement getters/setters
    public TeacherAgreement getAgreement() {
        return agreement;
    }

    public void setAgreement(TeacherAgreement agreement) {
        this.agreement = agreement;
    }

    // Audit fields getters/setters
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}