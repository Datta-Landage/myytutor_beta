package com.myytutor.dto;

public class SubjectDTO {
    public SubjectDTO(String subjectName, Integer classId) {
		super();
		this.subjectName = subjectName;
		this.classId = classId;
	}
	private String subjectName;
    private Integer classId;
    // Getters and setters
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public Integer getClassId() {
		return classId;
	}
	public void setClassId(Integer classId) {
		this.classId = classId;
	}
}