package com.myytutor.repository;

import com.myytutor.entity.EmailRateLimit;
import com.myytutor.entity.EmailRateLimit.EmailType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;

@Repository
public interface EmailRateLimitRepository extends JpaRepository<EmailRateLimit, Long> {
    
    /**
     * Count email attempts for specific email and type within time window
     */
    @Query("SELECT COUNT(e) FROM EmailRateLimit e WHERE e.email = :email AND e.emailType = :emailType AND e.createdAt >= :since")
    long countByEmailAndTypeAndCreatedAtAfter(
        @Param("email") String email, 
        @Param("emailType") EmailType emailType,
        @Param("since") LocalDateTime since
    );
    
    /**
     * Count email attempts from specific IP within time window
     */
    @Query("SELECT COUNT(e) FROM EmailRateLimit e WHERE e.ipAddress = :ipAddress AND e.emailType = :emailType AND e.createdAt >= :since")
    long countByIpAddressAndTypeAndCreatedAtAfter(
        @Param("ipAddress") String ipAddress,
        @Param("emailType") EmailType emailType,
        @Param("since") LocalDateTime since
    );
    
    /**
     * Get most recent attempt for email and type (for cooldown check)
     */
    @Query("SELECT e FROM EmailRateLimit e WHERE e.email = :email AND e.emailType = :emailType ORDER BY e.createdAt DESC LIMIT 1")
    EmailRateLimit findMostRecentByEmailAndType(
        @Param("email") String email,
        @Param("emailType") EmailType emailType
    );
    
    /**
     * Delete old records (cleanup - run daily via scheduled task)
     */
    @Modifying
    @Query("DELETE FROM EmailRateLimit e WHERE e.createdAt < :before")
    void deleteOldRecords(@Param("before") LocalDateTime before);
}
