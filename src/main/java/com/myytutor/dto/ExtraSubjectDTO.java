package com.myytutor.dto;

public class ExtraSubjectDTO {
    private String extraSubjectName;
    
    // No-argument constructor
    public ExtraSubjectDTO() {
    }

    public ExtraSubjectDTO(String extraSubjectName) {
        this.extraSubjectName = extraSubjectName;
    }

    public String getExtraSubjectName() {
        return extraSubjectName;
    }

    public void setExtraSubjectName(String extraSubjectName) {
        this.extraSubjectName = extraSubjectName;
    }
}
