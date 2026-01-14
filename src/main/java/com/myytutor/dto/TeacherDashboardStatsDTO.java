package com.myytutor.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherDashboardStatsDTO {
    private String fullName;
    private Integer sessionsDelivered;
    private Double rating;
    private Integer subjectsCount;
    private Boolean profileComplete; // Derived field for progress
}
