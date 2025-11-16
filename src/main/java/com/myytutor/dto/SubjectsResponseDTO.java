package com.myytutor.dto;

import java.util.List;
import java.util.Map;

public class SubjectsResponseDTO {
	  private Integer classId;
	    private List<Map<String, Object>> subjects;       // Contains {id, subjectName}
	    // Contains {id, extraSubjectName}

	    public SubjectsResponseDTO(Integer classId, List<Map<String, Object>> subjects) {
	        this.setClassId(classId);
	        this.setSubjects(subjects);
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
	    
	    

}
