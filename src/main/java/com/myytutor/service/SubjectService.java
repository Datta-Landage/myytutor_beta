package com.myytutor.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.myytutor.dto.SubjectResponseDTO;
import com.myytutor.dto.SubjectsResponseDTO;
import com.myytutor.repository.ExtraSubjectRepository;
import com.myytutor.repository.SubjectClassRepository;

@Service
public class SubjectService {

	@Autowired
	private SubjectClassRepository subjectClassRepository;

	@Autowired
	private ExtraSubjectRepository extraSubjectRepository;


	private static final Logger log = LoggerFactory.getLogger(SubjectService.class);

	public SubjectResponseDTO getSubjectsByClass(Integer classId) {
		log.info("inside get Subjects By Class for:", classId);
		List<Map<String, Object>> classSubjects = subjectClassRepository.findSubjectsByClassId(classId).stream()
				.map(subject -> {
					Map<String, Object> subjectMap = new HashMap<>();
					subjectMap.put("id", subject.getId());
					subjectMap.put("name", subject.getSubjectName());
					return subjectMap;
				}).collect(Collectors.toList());
		log.info("Fetched class subjects: {}", classSubjects);
		List<Map<String, Object>> extraSubjects = extraSubjectRepository.findAllExtraSubjects().stream()
				.map(extraSubject -> {
					Map<String, Object> extraSubjectMap = new HashMap<>();
					extraSubjectMap.put("id", extraSubject.getId());
					extraSubjectMap.put("name", extraSubject.getExtraSubjectName());
					return extraSubjectMap;
				}).collect(Collectors.toList());
		log.info("Fetched extra subjects: {}", extraSubjects);
		log.info("END: getSubjectsByClass for classId: {}", classId);

		return new SubjectResponseDTO(classId, classSubjects, extraSubjects);
	}

	public SubjectsResponseDTO getSubjects(Integer classId) {
		log.info("inside get Subjects By Class for registration:", classId);
		List<Map<String, Object>> classSubjects = subjectClassRepository.findSubjectsByClassId(classId).stream()
				.map(subject -> {
					Map<String, Object> subjectMap = new HashMap<>();
					subjectMap.put("id", subject.getId());
					subjectMap.put("name", subject.getSubjectName());
					return subjectMap;
				}).collect(Collectors.toList());
		log.info("After caling  find Subjects By ClassId: {}", classSubjects.toString());

		return new SubjectsResponseDTO(classId, classSubjects);
	}

	public SubjectsResponseDTO getExtraSubjects() {
		List<Map<String, Object>> extraSubjects = extraSubjectRepository.findAllExtraSubjects().stream()
				.map(extraSubject -> {
					Map<String, Object> extraSubjectMap = new HashMap<>();
					extraSubjectMap.put("id", extraSubject.getId());
					extraSubjectMap.put("name", extraSubject.getExtraSubjectName());
					return extraSubjectMap;
				}).collect(Collectors.toList());
		log.info("After caling  find  extra Subjects By ClassId for registration: {}", extraSubjects.toString());

		return new SubjectsResponseDTO(0, extraSubjects);
	}


}
