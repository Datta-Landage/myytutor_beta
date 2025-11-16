package com.myytutor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.myytutor.entity.SubjectClass;

@Repository
public interface SubjectClassRepository extends JpaRepository<SubjectClass, Long> {

    @Query("SELECT s FROM SubjectClass s WHERE s.classId = :classId")
    List<SubjectClass> findSubjectsByClassId(@Param("classId") Integer classId);
}
