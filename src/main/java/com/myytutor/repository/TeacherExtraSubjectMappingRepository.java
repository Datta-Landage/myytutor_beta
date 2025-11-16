package com.myytutor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.myytutor.entity.TeacherExtraSubjectMapping;

public interface TeacherExtraSubjectMappingRepository extends JpaRepository<TeacherExtraSubjectMapping, Long> {
    @Modifying
    @Query("DELETE FROM TeacherExtraSubjectMapping t WHERE t.teacher.id = :teacherId")
    void deleteByTeacherId(@Param("teacherId") Long teacherId);
}