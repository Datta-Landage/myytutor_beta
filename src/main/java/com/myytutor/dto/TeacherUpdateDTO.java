package com.myytutor.dto;

import lombok.Data;
import java.util.List;
import java.util.Set;

@Data
public class TeacherUpdateDTO {
    private String fullName;
    private String phoneNumber;
    private String whatsappNumber;
    private String aboutMe;
    private Integer experience;
    private String gender;
    private String hasVehicle;
    private String address;
    private String city;
    private String pin;
    private String state;
    private String country;
    private String mode;
    private Integer expectedFeePerHour;
    private String qualification;
    private List<String> preferredAreas;
    private String email;
    
    // Comprehensive updates
    private Set<Long> subjectIds;
    private Set<Long> extraSubjectIds;
    private List<TeacherEducationDTO> educations;
    private List<TeacherAvailabilityDTO> availabilities;
}
