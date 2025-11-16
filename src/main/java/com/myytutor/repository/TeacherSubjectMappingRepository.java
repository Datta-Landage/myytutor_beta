package com.myytutor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.myytutor.entity.TeacherSubjectMapping;

public interface TeacherSubjectMappingRepository extends JpaRepository<TeacherSubjectMapping, Long> {
    @Modifying
    @Query("DELETE FROM TeacherSubjectMapping t WHERE t.teacher.id = :teacherId")
    void deleteByTeacherId(@Param("teacherId") Long teacherId);
}