package com.myytutor.util;

import com.myytutor.dto.TeacherEducationDTO;
import com.myytutor.entity.TeacherEducation;
import com.myytutor.entity.Teacher;

import java.util.List;
import java.util.stream.Collectors;

public class TeacherEducationConverter {

    public static TeacherEducation toEntity(TeacherEducationDTO dto, Teacher teacher) {
        if (dto == null) {
            return null;
        }

        TeacherEducation entity = new TeacherEducation();
        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        entity.setDegree(dto.getDegree());
        entity.setInstitution(dto.getInstitution());
        entity.setPassingYear(dto.getPassingYear());
        entity.setGrade(dto.getGrade());
        entity.setTeacher(teacher);

        return entity;
    }

    public static TeacherEducationDTO toDto(TeacherEducation entity) {
        if (entity == null) {
            return null;
        }

        return new TeacherEducationDTO(
                entity.getId(),
                entity.getDegree(),
                entity.getInstitution(),
                entity.getPassingYear(),
                entity.getGrade());
    }

    public static List<TeacherEducationDTO> toDtoList(List<TeacherEducation> entities) {
        if (entities == null) {
            return null;
        }

        return entities.stream()
                .map(TeacherEducationConverter::toDto)
                .collect(Collectors.toList());
    }

    public static List<TeacherEducation> toEntityList(List<TeacherEducationDTO> dtos, Teacher teacher) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream()
                .map(dto -> toEntity(dto, teacher))
                .collect(Collectors.toList());
    }
}