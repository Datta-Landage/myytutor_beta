package com.myytutor.dto;

import java.util.List;
import java.util.Map;


public class SubjectResponseDTO {

    private Integer classId;
    private List<Map<String, Object>> subjects;       // Contains {id, subjectName}
    private List<Map<String, Object>> extraSubjects; // Contains {id, extraSubjectName}

    public SubjectResponseDTO(Integer classId, List<Map<String, Object>> subjects, List<Map<String, Object>> extraSubjects) {
        this.setClassId(classId);
        this.setSubjects(subjects);
        this.setExtraSubjects(extraSubjects);
    }

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public List<Map<String, Object>> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Map<String, Object>> subjects) {
		this.subjects = subjects;
	}

	public List<Map<String, Object>> getExtraSubjects() {
		return extraSubjects;
	}

	public void setExtraSubjects(List<Map<String, Object>> extraSubjects) {
		this.extraSubjects = extraSubjects;
	}

    // Getters and Setters
}

