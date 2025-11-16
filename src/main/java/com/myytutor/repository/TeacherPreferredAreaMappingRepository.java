package com.myytutor.repository;

import com.myytutor.entity.TeacherPreferredAreaMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherPreferredAreaMappingRepository extends JpaRepository<TeacherPreferredAreaMapping, Long> {
    void deleteByTeacherId(Long teacherId);
}
