package com.myytutor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.myytutor.entity.Teacher;
import java.time.LocalDateTime;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
	// Teacher entity doesn't have a 'username' field. Use email for lookup instead.
	Teacher findByEmail(String email);

	/**
	 * Delete unverified teacher accounts where OTP has expired
	 * @param cutoffTime The time before which OTPs are considered expired
	 * @return Number of deleted records
	 */
	int deleteByEmailVerifiedFalseAndEmailOtpGeneratedAtBefore(LocalDateTime cutoffTime);

	/**
	 * Clear expired OTP fields from verified accounts
	 * @param cutoffTime The time before which OTPs should be cleared
	 * @return Number of updated records
	 */
	@Modifying
	@Query("UPDATE Teacher t SET t.emailOtp = null, t.emailOtpGeneratedAt = null " +
		   "WHERE t.emailVerified = true AND t.emailOtpGeneratedAt < :cutoffTime")
	int clearExpiredOtps(@Param("cutoffTime") LocalDateTime cutoffTime);
}
