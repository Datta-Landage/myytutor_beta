package com.myytutor.dto;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeacherProfileDTO {
    private Long id;
    private String slug;
    private String fullName;
    private String profilePhoto;
    private Integer experience;
    private String aboutMe;
    private String city;
    private String qualification;
    private String gender;
    private String phoneNumber;
    private String whatsappNumber;
    private String hasVehicle;
    private String address;
    private String pin;
    private String state;
    private String country;
    
    // Subject mapping: Class Level -> List of Subjects
    private Map<String, List<String>> subjects;
    
    // Raw IDs for easier frontend management
    private Set<Long> rawSubjectIds;
    private Set<Long> rawExtraSubjectIds;
    
    // Extra subjects
    private Map<String, List<String>> extraSubjects;
    
    // Boards (derived from subjects or a separate mapping if exists)
    private Map<String, List<String>> boards;
    
    // Availability
    private List<TeacherAvailabilityDTO> availability;
    
    // Education
    private List<TeacherEducationDTO> education;
    
    private Integer sessionsDelivered;
    private Double rating;
    
    // Additional Details
    private List<String> preferredAreas;
    private String mode; // ONLINE, OFFLINE, BOTH
    private Integer expectedFee;
}
