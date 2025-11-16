package com.myytutor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myytutor.entity.InquiryExtraSubjectMapping;


@Repository
public interface InquiryExtraSubjectMappingRepository extends JpaRepository<InquiryExtraSubjectMapping, Long> {
	
}
