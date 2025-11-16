package com.myytutor.service.test;

import com.myytutor.dto.SubjectResponseDTO;
import com.myytutor.entity.ExtraSubject;
import com.myytutor.entity.SubjectClass;
import com.myytutor.repository.ExtraSubjectRepository;
import com.myytutor.repository.SubjectClassRepository;
import com.myytutor.service.SubjectService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@SpringBootTest
@ActiveProfiles("test")
public class SubjectServiceTest {

    @Mock
    private SubjectClassRepository subjectClassRepository;

    @Mock
    private ExtraSubjectRepository extraSubjectRepository;

    @InjectMocks
    private SubjectService subjectService;

    private int classId;
    private List<SubjectClass> subjectClassList;
    private List<ExtraSubject> extraSubjectList;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        classId = 10;

        SubjectClass subject1 = new SubjectClass();
        subject1.setId(1L);
        subject1.setClassId(10);
        subject1.setSubjectName("Math");

        SubjectClass subject2 = new SubjectClass();
        subject2.setId(2L);
        subject1.setClassId(10);
        subject2.setSubjectName("Science");

        ExtraSubject extra1 = new ExtraSubject();
        extra1.setId(1001L);
        extra1.setExtraSubjectName("Java");

        ExtraSubject extra2 = new ExtraSubject();
        extra2.setId(1002L);
        extra2.setExtraSubjectName("Python");
        
        subjectClassList = Arrays.asList(subject1, subject2);
        extraSubjectList = Arrays.asList(extra1, extra2);
    }

    @Test
    public void testGetSubjectsByClass_ShouldReturnSubjectsAndExtraSubjects() {
        // Arrange
        when(subjectClassRepository.findSubjectsByClassId(classId)).thenReturn(subjectClassList);
        when(extraSubjectRepository.findAllExtraSubjects()).thenReturn(extraSubjectList);

        // Act
        SubjectResponseDTO responseDTO = subjectService.getSubjectsByClass(classId);

        // Assert
        assertNotNull(responseDTO);
        assertEquals(classId, responseDTO.getClassId());

        List<Map<String, Object>> classSubjects = responseDTO.getSubjects();
        assertEquals(2, classSubjects.size());
        assertEquals("Math", classSubjects.get(0).get("name"));
        assertEquals("Science", classSubjects.get(1).get("name"));

        List<Map<String, Object>> extraSubjects = responseDTO.getExtraSubjects();
        assertEquals(2, extraSubjects.size());
        assertEquals("Java", extraSubjects.get(0).get("name"));
        assertEquals("Python", extraSubjects.get(1).get("name"));
    }
}
