package com.myytutor.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.Year;

public class TeacherEducationDTO {
    private Long id;

    @NotBlank(message = "Degree is required")
    @Size(min = 2, max = 100, message = "Degree must be between 2 and 100 characters")
    private String degree;

    @NotBlank(message = "Institution is required")
    @Size(min = 2, max = 200, message = "Institution name must be between 2 and 200 characters")
    private String institution;

    @NotNull(message = "Passing year is required")
    @Min(value = 1950, message = "Passing year must be after 1950")
    @Max(value = 2100, message = "Passing year must be before 2100")
    private Integer passingYear;

    @NotBlank(message = "Grade is required")
    @Pattern(regexp = "^(A\\+|A|B\\+|B|C\\+|C|D|F|First Class|Second Class|Third Class|Pass|[0-9]{1,2}(\\.\\d{1,2})?%?)$", message = "Invalid grade format. Allowed formats: A+, A, B+, B, C+, C, D, F, First Class, Second Class, Third Class, Pass, or percentage (e.g., 85% or 85.5%)")
    private String grade;

    // Default constructor
    public TeacherEducationDTO() {
    }

    // Constructor with all fields
    public TeacherEducationDTO(Long id, String degree, String institution, Integer passingYear, String grade) {
        this.id = id;
        this.degree = degree;
        this.institution = institution;
        this.passingYear = passingYear;
        this.grade = grade;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public Integer getPassingYear() {
        return passingYear;
    }

    public void setPassingYear(Integer passingYear) {
        this.passingYear = passingYear;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    // Custom validation method
    public void validate() {
        // Check if passing year is not in the future
        if (passingYear != null && passingYear > Year.now().getValue()) {
            throw new IllegalArgumentException("Passing year cannot be in the future");
        }

        // Standardize grade format
        if (grade != null) {
            grade = grade.trim().toUpperCase();
            // Convert percentage without % to proper format
            if (grade.matches("^\\d+(\\.\\d+)?$")) {
                grade = grade + "%";
            }
        }
    }
}