package com.myytutor.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "inquiry")
public class Inquiry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phone;

    @Column(name = "class_standard")
    private String classStandard;

    @Column(name = "board")
    private String board;

    @Column(name = "address", length = 500)
    private String address;

    @Column(length = 500)
    private String message;

    @Column(name = "selected_start_date")
    private LocalDate selectedStartDate;

    @Column(name = "selected_end_date")
    private LocalDate selectedEndDate;

    @Column(name = "selected_start_time")
    private Integer selectedStartTime;

    @Column(name = "selected_end_time")
    private Integer selectedEndTime;

    @Column(name = "privacy_accepted", nullable = false)
    private Boolean privacyAccepted;

    @Column(name = "privacy_version")
    private String privacyVersion;

    @Column(name = "privacy_accepted_at")
    private LocalDateTime privacyAcceptedAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "inquiry", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<InquirySubjectClassMapping> subjectMappings = new HashSet<>();

    @OneToMany(mappedBy = "inquiry", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<InquiryExtraSubjectMapping> extraSubjectMappings = new HashSet<>();

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getPrivacyAccepted() {
        return privacyAccepted;
    }

    public void setPrivacyAccepted(Boolean privacyAccepted) {
        this.privacyAccepted = privacyAccepted;
    }

    public String getPrivacyVersion() {
        return privacyVersion;
    }

    public void setPrivacyVersion(String privacyVersion) {
        this.privacyVersion = privacyVersion;
    }

    public LocalDateTime getPrivacyAcceptedAt() {
        return privacyAcceptedAt;
    }

    public void setPrivacyAcceptedAt(LocalDateTime privacyAcceptedAt) {
        this.privacyAcceptedAt = privacyAcceptedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getClassStandard() {
        return classStandard;
    }

    public void setClassStandard(String classStandard) {
        this.classStandard = classStandard;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getSelectedStartDate() {
        return selectedStartDate;
    }

    public void setSelectedStartDate(LocalDate selectedStartDate) {
        this.selectedStartDate = selectedStartDate;
    }

    public LocalDate getSelectedEndDate() {
        return selectedEndDate;
    }

    public void setSelectedEndDate(LocalDate selectedEndDate) {
        this.selectedEndDate = selectedEndDate;
    }

    public Integer getSelectedStartTime() {
        return selectedStartTime;
    }

    public void setSelectedStartTime(Integer selectedStartTime) {
        this.selectedStartTime = selectedStartTime;
    }

    public Integer getSelectedEndTime() {
        return selectedEndTime;
    }

    public void setSelectedEndTime(Integer selectedEndTime) {
        this.selectedEndTime = selectedEndTime;
    }

    public Set<InquirySubjectClassMapping> getSubjectMappings() {
        return subjectMappings;
    }

    public void setSubjectMappings(Set<InquirySubjectClassMapping> subjectMappings) {
        this.subjectMappings = subjectMappings;
    }

    public Set<InquiryExtraSubjectMapping> getExtraSubjectMappings() {
        return extraSubjectMappings;
    }

    public void setExtraSubjectMappings(Set<InquiryExtraSubjectMapping> extraSubjectMappings) {
        this.extraSubjectMappings = extraSubjectMappings;
    }
}

