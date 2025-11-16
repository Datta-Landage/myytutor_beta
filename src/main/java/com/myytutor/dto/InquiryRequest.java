package com.myytutor.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

public class InquiryRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @Pattern(regexp = "^(\\+91)?[0-9]{10}$", message = "Phone must be 10 digits, optionally prefixed with +91")
    private String phone;

    @NotBlank(message = "Class is required")
    private String classStandard;

    @NotBlank(message = "Board is required")
    private String board;

    @NotBlank(message = "Address is required")
    @Size(max = 500, message = "Address must not exceed 500 characters")
    private String address;

    @Size(max = 500, message = "Message must not exceed 500 characters")
    private String message;

    @NotNull(message = "Selected start date is required")
    private LocalDate selectedStartDate;

    @NotNull(message = "Selected end date is required")
    private LocalDate selectedEndDate;

    @NotNull(message = "Selected start time is required")
    @Min(value = 0, message = "Start time must be between 0 and 1440")
    @Max(value = 1440, message = "Start time must be between 0 and 1440")
    private Integer selectedStartTime;

    @NotNull(message = "Selected end time is required")
    @Min(value = 0, message = "End time must be between 0 and 1440")
    @Max(value = 1440, message = "End time must be between 0 and 1440")
    private Integer selectedEndTime;

    @NotEmpty(message = "At least one subject must be selected")
    private List<Long> selectedSubjectIds;

    private List<Long> selectedExtraSubjectIds;

    @AssertTrue(message = "Please accept the Privacy Policy before submitting the inquiry")
    private boolean privacyAccepted;

    @NotBlank(message = "Privacy policy version is required")
    private String privacyVersion;

    // Getters & Setters
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public List<Long> getSelectedSubjectIds() {
        return selectedSubjectIds;
    }

    public void setSelectedSubjectIds(List<Long> selectedSubjectIds) {
        this.selectedSubjectIds = selectedSubjectIds;
    }

    public List<Long> getSelectedExtraSubjectIds() {
        return selectedExtraSubjectIds;
    }

    public void setSelectedExtraSubjectIds(List<Long> selectedExtraSubjectIds) {
        this.selectedExtraSubjectIds = selectedExtraSubjectIds;
    }

    public boolean isPrivacyAccepted() {
        return privacyAccepted;
    }

    public void setPrivacyAccepted(boolean privacyAccepted) {
        this.privacyAccepted = privacyAccepted;
    }

    public String getPrivacyVersion() {
        return privacyVersion;
    }

    public void setPrivacyVersion(String privacyVersion) {
        this.privacyVersion = privacyVersion;
    }
}

