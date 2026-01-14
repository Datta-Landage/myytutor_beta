package com.myytutor.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class TeacherSubjectsDTO {
    private Map<String, List<String>> subjects;
    private Map<String, List<String>> extraSubjects;
    private Set<Long> rawSubjectIds;
    private Set<Long> rawExtraSubjectIds;
    private Map<String, List<String>> boards;
}
