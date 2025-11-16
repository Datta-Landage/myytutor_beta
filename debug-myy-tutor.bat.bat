  @Query(value = """
        SELECT
            t.id AS teacherId,
            t.name,
            t.gender,
            t.city,
            t.experience,
            t.rating,
            t.profile_photo AS profilePhoto,
            (
                SELECT GROUP_CONCAT(DISTINCT sc.subject_name SEPARATOR ', ')
                FROM teacher_subject_mapping tsm
                JOIN subject_class sc ON sc.id = tsm.subject_class_id
                WHERE tsm.teacher_id = t.id
                  AND (:selectedClassIds IS NULL OR sc.id IN (:selectedClassIds))
            ) AS matchingClassSubjects,
            (
                SELECT GROUP_CONCAT(DISTINCT es.extra_subject_name SEPARATOR ', ')
                FROM teacher_extra_subject_mapping tesm
                JOIN extra_subject es ON es.id = tesm.extra_subject_id
                WHERE tesm.teacher_id = t.id
                  AND (:selectedExtraSubjectIds IS NULL OR es.id IN (:selectedExtraSubjectIds))
            ) AS matchingExtraSubjects,
            (
                SELECT GROUP_CONCAT(DISTINCT b.board_name SEPARATOR ', ')
                FROM teacher_board_mapping tbm
                JOIN boards b ON b.id = tbm.board_id
                WHERE tbm.teacher_id = t.id
            ) AS matchingBoards,
            ANY_VALUE(ta.available_time_for_slot) AS availableTimeForSlot,
            ANY_VALUE(ta.start_time) AS startTime,
            ANY_VALUE(ta.end_time) AS endTime,
            CASE
                WHEN EXISTS (
                    SELECT 1
                    FROM bookings b
                    WHERE b.teacher_id = t.id
                      AND b.is_active = TRUE
                      AND b.plan_start_date <= :selectedEndDate
                      AND b.plan_end_date >= :selectedStartDate
                      AND b.plan_start_time < :selectedEndTime
                      AND b.plan_end_time > :selectedStartTime
                ) THEN 1 ELSE 0
            END AS hasBookingConflict,
            ANY_VALUE(ta.available_time_for_slot - COALESCE(
                (
                    SELECT SUM(LEAST(b.plan_end_time, :selectedEndTime) - GREATEST(b.plan_start_time, :selectedStartTime))
                    FROM bookings b
                    WHERE b.teacher_id = t.id
                      AND b.is_active = TRUE
                      AND b.plan_start_date <= :selectedEndDate
                      AND b.plan_end_date >= :selectedStartDate
                      AND b.plan_start_time < :selectedEndTime
                      AND b.plan_end_time > :selectedStartTime
                ), 0)
            ) AS adjustedAvailableTime,
            ANY_VALUE(ta.total_day_availability_for_slot) AS weeklyAvailabilityScore,
            CASE
                WHEN :userLatitude IS NOT NULL AND :userLongitude IS NOT NULL
                THEN ST_Distance_Sphere(POINT(t.longitude, t.latitude), POINT(:userLongitude, :userLatitude)) / 1000.0
                ELSE NULL
            END AS distanceToUser,
            COUNT(t.id) OVER () AS totalTeachers
        FROM teachers t
        JOIN teacher_availability ta ON ta.teacher_id = t.id
             AND (ta.start_time <= :selectedStartTime AND ta.end_time >= :selectedEndTime)
        WHERE
            t.is_ative = TRUE
            AND t.is_verified = TRUE
            AND (:filterGender IS NULL OR t.gender = :filterGender)
            AND (:minRating IS NULL OR t.rating >= :minRating)
            AND (:minExperience IS NULL OR t.experience >= :minExperience)
            AND (:minAvailabilityMinutes IS NULL OR ta.available_time_for_slot >= :minAvailabilityMinutes)
            AND (
                (:selectedClassIds IS NOT NULL AND EXISTS (
                    SELECT 1 FROM teacher_subject_mapping tsm
                    JOIN subject_class sc ON sc.id = tsm.subject_class_id
                    WHERE tsm.teacher_id = t.id
                      AND sc.id IN (:selectedClassIds)
                ))
                OR
                (:selectedExtraSubjectIds IS NOT NULL AND EXISTS (
                    SELECT 1 FROM teacher_extra_subject_mapping tesm
                    JOIN extra_subject es ON es.id = tesm.extra_subject_id
                    WHERE tesm.teacher_id = t.id
                      AND es.id IN (:selectedExtraSubjectIds)
                ))
            )
            AND (:filterBoards IS NULL OR EXISTS (
                SELECT 1 FROM teacher_board_mapping tbm
                JOIN boards b ON b.id = tbm.board_id
                WHERE tbm.teacher_id = t.id
                  AND b.board_name IN (:filterBoards)
            ))
            AND ST_Distance_Sphere(POINT(t.longitude, t.latitude), POINT(:userLongitude, :userLatitude))
                <= :maxDistanceInKm * 1000
        GROUP BY t.id
        ORDER BY
            hasBookingConflict ASC,
            (SELECT COUNT(*) FROM teacher_subject_mapping tsm2
             WHERE tsm2.teacher_id = t.id
               AND (:selectedClassIds IS NULL OR tsm2.subject_class_id IN (:selectedClassIds))) DESC,
            adjustedAvailableTime DESC,
            distanceToUser ASC
        LIMIT :limit OFFSET :offset
        """, nativeQuery = true)
    List<Object[]> findAvailableTeachers(
            @Param("selectedClassIds") List<Long> selectedClassIds,
            @Param("selectedExtraSubjectIds") List<Long> selectedExtraSubjectIds,
            @Param("selectedStartDate") LocalDate selectedStartDate,
            @Param("selectedEndDate") LocalDate selectedEndDate,
            @Param("selectedStartTime") Integer selectedStartTime,
            @Param("selectedEndTime") Integer selectedEndTime,
            @Param("userLatitude") Double userLatitude,
            @Param("userLongitude") Double userLongitude,
            @Param("maxDistanceInKm") Double maxDistanceInKm,
            @Param("filterGender") Boolean filterGender,
            @Param("minRating") Double minRating,
            @Param("minExperience") Double minExperience,
            @Param("filterBoards") List<String> filterBoards,
            @Param("minAvailabilityMinutes") Integer minAvailabilityMinutes,
            @Param("limit") Integer limit,
            @Param("offset") Integer offset