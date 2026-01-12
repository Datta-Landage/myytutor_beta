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

	// Find by slug for public profile
	java.util.Optional<Teacher> findBySlug(String slug);

	// Check slug existence for uniqueness
	boolean existsBySlug(String slug);

	/**
	 * Check if a teacher with verified email and completed registration exists
	 * 
	 * @param email The email to check
	 * @return true if teacher is fully registered, false otherwise
	 */
	@Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Teacher t " +
			"WHERE t.email = :email AND t.emailVerified = true AND t.fullName IS NOT NULL")
	boolean existsByEmailAndFullyRegistered(@Param("email") String email);

	/**
	 * Delete unverified teacher accounts where OTP has expired
	 * 
	 * @param cutoffTime The time before which OTPs are considered expired
	 * @return Number of deleted records
	 */
	int deleteByEmailVerifiedFalseAndEmailOtpGeneratedAtBefore(LocalDateTime cutoffTime);

	/**
	 * Clear expired OTP fields from verified accounts
	 * 
	 * @param cutoffTime The time before which OTPs should be cleared
	 * @return Number of updated records
	 */
	@Modifying
	@Query("UPDATE Teacher t SET t.emailOtp = null, t.emailOtpGeneratedAt = null " +
			"WHERE t.emailVerified = true AND t.emailOtpGeneratedAt < :cutoffTime")
	int clearExpiredOtps(@Param("cutoffTime") LocalDateTime cutoffTime);

	/**
	 * Find all verified teachers registered since a specific time
	 * Used for daily registration notifications
	 * 
	 * @param startTime The time from which to fetch registered teachers
	 * @return List of teachers registered after the given time
	 */
	@Query("SELECT t FROM Teacher t WHERE t.emailVerified = true AND t.fullName IS NOT NULL AND t.createdAt >= :startTime ORDER BY t.createdAt DESC")
	java.util.List<Teacher> findTeachersRegisteredSince(@Param("startTime") LocalDateTime startTime);
}
