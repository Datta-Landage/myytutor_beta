package com.myytutor.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.myytutor.dto.SubjectResponseDTO;
import com.myytutor.dto.SubjectsResponseDTO;
import com.myytutor.service.SubjectService;

@RestController
@RequestMapping(value="/api")
@CrossOrigin
public class SubjectController {
    private static final Logger log = LoggerFactory.getLogger(SubjectController.class);
    @Autowired
    private SubjectService subjectService;

    @GetMapping(value="/launchPad")
    public ResponseEntity<SubjectResponseDTO> getSubjects(@RequestParam Integer classId) {
        log.info("Received request for launchPad subjects for classId: {}", classId);

        SubjectResponseDTO response = subjectService.getSubjectsByClass(classId);
        log.info("Completed request for launchPad subjects for classId: {}", classId);
        return ResponseEntity.ok(response);
    }
    @GetMapping(value="/subjects")
    public ResponseEntity<SubjectsResponseDTO> getSubject(@RequestParam Integer classId) {
        log.info("Received request for subjects for classId: {}", classId);

        SubjectsResponseDTO response = subjectService.getSubjects(classId);
        log.info("Completed request for subjects for classId: {}", classId);
        return ResponseEntity.ok(response);
    }
    @GetMapping(value="/extrasubjects")
    public ResponseEntity<SubjectsResponseDTO> getExtraSubjects() {
        log.info("Received request for extra subjects");

        SubjectsResponseDTO response = subjectService.getExtraSubjects();
        log.info("Completed request for extra subjects");
        return ResponseEntity.ok(response);
    }
 
}