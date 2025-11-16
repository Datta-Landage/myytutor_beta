package com.myytutor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myytutor.entity.InquirySubjectClassMapping;

@Repository
public interface InquirySubjectClassMappingRepository extends JpaRepository<InquirySubjectClassMapping, Long> {
}
