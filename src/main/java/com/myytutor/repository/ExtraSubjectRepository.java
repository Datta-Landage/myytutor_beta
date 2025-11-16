package com.myytutor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.myytutor.entity.ExtraSubject;

@Repository
public interface ExtraSubjectRepository extends JpaRepository<ExtraSubject, Long> {

    @Query("SELECT e FROM ExtraSubject e")
    List<ExtraSubject> findAllExtraSubjects();
}