package com.myytutor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.myytutor.entity.TeacherEducation;
import java.util.List;

public interface TeacherEducationRepository extends JpaRepository<TeacherEducation, Long> {
    List<TeacherEducation> findByTeacherId(Long teacherId);
    
    void deleteByTeacherId(Long teacherId);
    
    @Query("SELECT te FROM TeacherEducation te WHERE te.teacher.id = :teacherId ORDER BY te.passingYear DESC")
    List<TeacherEducation> findByTeacherIdOrderByPassingYearDesc(@Param("teacherId") Long teacherId);
    
    @Query("SELECT COUNT(te) > 0 FROM TeacherEducation te WHERE te.teacher.id = :teacherId")
    boolean hasEducationRecords(@Param("teacherId") Long teacherId);
}