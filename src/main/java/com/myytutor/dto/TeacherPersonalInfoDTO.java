package com.myytutor.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class TeacherPersonalInfoDTO {
    private Long id;
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
    private List<String> preferredAreas;
    private String mode;
    private Integer expectedFee;
    private String email;
}
