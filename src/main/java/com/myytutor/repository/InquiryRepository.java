package com.myytutor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.myytutor.entity.Inquiry;
import java.time.LocalDateTime;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
    
    /**
     * Count inquiries from a phone number within a time range
     * @param phone The phone number to check
     * @param startOfDay The start of the day (00:00:00)
     * @param endOfDay The end of the day (23:59:59)
     * @return Count of inquiries for this phone number today
     */
    @Query("SELECT COUNT(i) FROM Inquiry i WHERE i.phone = :phone AND i.createdAt BETWEEN :startOfDay AND :endOfDay")
    long countByPhoneAndCreatedAtBetween(
        @Param("phone") String phone,
        @Param("startOfDay") LocalDateTime startOfDay,
        @Param("endOfDay") LocalDateTime endOfDay
    );
}



