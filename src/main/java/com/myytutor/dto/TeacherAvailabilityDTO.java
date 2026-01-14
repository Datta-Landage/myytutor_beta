package com.myytutor.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class TeacherAvailabilityDTO {
    @NotNull(message = "Start time is required")
    @Min(value = 0, message = "Start time must be between 0 and 1440 minutes (0:00 to 24:00)")
    @Max(value = 1440, message = "Start time must be between 0 and 1440 minutes (0:00 to 24:00)")
    private Integer startTime; // minutes since midnight (0-1440)

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull(message = "End time is required")
    @Min(value = 0, message = "End time must be between 0 and 1440 minutes (0:00 to 24:00)")
    @Max(value = 1440, message = "End time must be between 0 and 1440 minutes (0:00 to 24:00)")
    private Integer endTime; // minutes since midnight (0-1440)

    @NotNull(message = "Available time for slot is required")
    @Min(value = 30, message = "Minimum slot duration is 30 minutes")
    @Max(value = 480, message = "Maximum slot duration is 480 minutes (8 hours)")
    private Integer availableTimeForSlot; // Preferred duration for each booking

    @NotNull(message = "Monday availability is required")
    private Boolean monday = false;

    @NotNull(message = "Tuesday availability is required")
    private Boolean tuesday = false;

    @NotNull(message = "Wednesday availability is required")
    private Boolean wednesday = false;

    @NotNull(message = "Thursday availability is required")
    private Boolean thursday = false;

    @NotNull(message = "Friday availability is required")
    private Boolean friday = false;

    @NotNull(message = "Saturday availability is required")
    private Boolean saturday = false;

    @NotNull(message = "Sunday availability is required")
    private Boolean sunday = false;

    // Constructor
    public TeacherAvailabilityDTO() {
    }

    // Getters and Setters
    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public Integer getAvailableTimeForSlot() {
        return availableTimeForSlot;
    }

    public void setAvailableTimeForSlot(Integer availableTimeForSlot) {
        this.availableTimeForSlot = availableTimeForSlot;
    }

    public Boolean getMonday() {
        return monday;
    }

    public void setMonday(Boolean monday) {
        this.monday = monday;
    }

    public Boolean getTuesday() {
        return tuesday;
    }

    public void setTuesday(Boolean tuesday) {
        this.tuesday = tuesday;
    }

    public Boolean getWednesday() {
        return wednesday;
    }

    public void setWednesday(Boolean wednesday) {
        this.wednesday = wednesday;
    }

    public Boolean getThursday() {
        return thursday;
    }

    public void setThursday(Boolean thursday) {
        this.thursday = thursday;
    }

    public Boolean getFriday() {
        return friday;
    }

    public void setFriday(Boolean friday) {
        this.friday = friday;
    }

    public Boolean getSaturday() {
        return saturday;
    }

    public void setSaturday(Boolean saturday) {
        this.saturday = saturday;
    }

    public Boolean getSunday() {
        return sunday;
    }

    public void setSunday(Boolean sunday) {
        this.sunday = sunday;
    }
}