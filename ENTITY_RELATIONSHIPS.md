# Teacher Entity Relationships Documentation

## Overview
This document explains the entity relationships in the Teacher registration system, specifically focusing on the Teacher entity and its related entities.

## Entity Structure

### Teacher Entity
The main entity that represents a teacher in the system.

#### Core Fields
- **Authentication & Verification**
  - `id` (Long) - Primary key
  - `email` (String) - Unique, used for login
  - `password` (String) - BCrypt encoded
  - `emailVerified` (Boolean) - Email verification status
  - `emailOtp` (String) - OTP for email verification
  - `emailOtpGeneratedAt` (LocalDateTime) - OTP timestamp
  - `emailVerifiedAt` (LocalDateTime) - Verification timestamp

- **Basic Information**
  - `fullName` (String)
  - `phoneNumber` (String)
  - `whatsappNumber` (String)
  - `gender` (String)
  - `dateOfBirth` (LocalDateTime)
  - `aboutMe` (String, max 2000 chars)
  - `experience` (Integer)

- **Location & Contact**
  - `address` (String)
  - `city` (String)
  - `pin` (String)
  - `state` (String)
  - `country` (String)

- **Teaching Preferences**
  - `mode` (String) - Online/Offline/Both
  - `hasVehicle` (String)
  - `expectedFeePerHour` (Integer)
  - `preferredAreas` (Set<String>) - Using @ElementCollection

- **Qualifications**
  - `qualifications` (String, max 1000 chars)
  - `certifications` (String, max 1000 chars)

- **Audit**
  - `createdAt` (LocalDateTime) - Auto-set on creation

---

## Relationships

### 1. Teacher ↔ TeacherAvailability (One-to-Many)
**Mapping:** `@OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)`

**Constraints:**
- **Minimum:** 1 availability slot required
- **Maximum:** 3 availability slots allowed

**TeacherAvailability Fields:**
- `id` (Long)
- `teacher` (Teacher) - Foreign key
- `startTime` (Integer) - Minutes since midnight
- `endTime` (Integer) - Minutes since midnight
- `availableTimeForSlot` (Integer) - Available minutes
- `monday`, `tuesday`, `wednesday`, `thursday`, `friday`, `saturday`, `sunday` (Boolean)
- `totalDayAvailabilityForSlot` (Integer) - Auto-calculated count

**Validation:** DTO level `@Size(min = 1, max = 3)` and service level checks

---

### 2. Teacher ↔ TeacherEducation (One-to-Many)
**Mapping:** `@OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)`

**Constraints:**
- **Minimum:** 1 education entry required
- **Maximum:** 3 education entries allowed

**TeacherEducation Fields:**
- `id` (Long)
- `teacher` (Teacher) - Foreign key
- `degree` (String, 2-100 chars)
- `institution` (String, 2-200 chars)
- `passingYear` (Integer, 1950-2100)
- `grade` (String) - Pattern validated (A+, First Class, 85%, etc.)

**Validation:** 
- DTO level `@Size(min = 1, max = 3)` 
- Service level checks in `updateTeacherEducations()`
- Custom validation in `TeacherEducationDTO.validate()`

---

### 3. Teacher ↔ TeacherAgreement (One-to-One)
**Mapping:** `@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)`

**Constraints:**
- **Required:** Exactly ONE agreement per teacher (at registration)
- **Cannot be null** during full registration

**TeacherAgreement Fields:**
- `id` (Long)
- `privacyPolicy` (Document) - Foreign key to Document entity
- `privacyPolicyAcceptedAt` (LocalDateTime)
- `termsOfUse` (Document) - Foreign key to Document entity
- `termsOfUseAcceptedAt` (LocalDateTime)
- `teacherAgreement` (Document) - Foreign key to Document entity
- `teacherAgreementAcceptedAt` (LocalDateTime)
- `createdAt` (LocalDateTime) - Auto-set
- `updatedAt` (LocalDateTime) - Auto-updated

**Note:** Agreement references global Document entities (privacy policy, terms, etc.)

---

### 4. Teacher ↔ TeacherSubjectMapping (One-to-Many)
**Mapping:** `@OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)`

**Purpose:** Links teachers to main subjects they teach

---

### 5. Teacher ↔ TeacherExtraSubjectMapping (One-to-Many)
**Mapping:** `@OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)`

**Purpose:** Links teachers to additional subjects they can teach

---

## Registration Flow

### Step 1: Email Verification (OTP)
**Endpoint:** `POST /api/v1/teachers/send-otp`

**Process:**
1. Create Teacher with minimal data:
   - `email`
   - `emailOtp` (6-digit random)
   - `emailOtpGeneratedAt`
   - `emailVerified = false`
2. Send OTP email
3. All other fields remain NULL (allowed during this phase)

**Fields Required:** `email` only

---

### Step 2: OTP Verification
**Endpoint:** `POST /api/v1/teachers/verify-otp`

**Process:**
1. Validate OTP (must be within 5 minutes)
2. Mark email as verified:
   - `emailVerified = true`
   - `emailVerifiedAt = current timestamp`
3. Clear OTP fields

**Fields Required:** `email`, `emailOtp`

---

### Step 3: Full Registration
**Endpoint:** `POST /api/v1/teachers/register`

**Process:**
1. Verify email is already verified
2. Update all teacher fields
3. Create TeacherAgreement (ONE, required)
4. Create TeacherAvailability entries (1-3, required)
5. Create TeacherEducation entries (1-3, required)
6. Create subject mappings
7. Send welcome email

**Fields Required:** All fields in `TeacherRegistrationRequest`

---

## Database Cascade Behavior

### CASCADE.ALL
All child entities (TeacherAvailability, TeacherEducation, TeacherAgreement, Subject Mappings) will be:
- **Created** when teacher is created
- **Updated** when teacher is updated
- **Deleted** when teacher is deleted

### orphanRemoval = true
If a child entity is removed from the parent's collection, it will be automatically deleted from the database.

---

## Validation Summary

| Entity | Field/Collection | Min | Max | Validation Level |
|--------|------------------|-----|-----|------------------|
| Teacher | availabilities | 1 | 3 | DTO + Service |
| Teacher | educations | 1 | 3 | DTO + Service |
| Teacher | agreement | 1 | 1 | DTO + Service |
| TeacherEducation | degree | 2 chars | 100 chars | DTO |
| TeacherEducation | institution | 2 chars | 200 chars | DTO |
| TeacherEducation | passingYear | 1950 | 2100 | DTO |
| TeacherAvailability | startTime | - | endTime | Service |

---

## Important Notes

1. **Two-Phase Creation:** Teachers are created with minimal data (email + OTP) first, then fully populated after email verification.

2. **Nullable Fields:** During OTP phase, all fields except `email`, `emailOtp`, `emailOtpGeneratedAt`, and `emailVerified` are allowed to be NULL.

3. **Removed Fields:** 
   - Old `education` String field removed (replaced with `educations` collection)
   - Old `phoneNumber` field from incorrect location fixed

4. **Collection Validation:** Both DTO-level (`@Size`) and service-level validation ensure min/max constraints on collections.

5. **Agreement Structure:** TeacherAgreement is a separate entity that references global Document entities for legal documents.

---

## Example Registration JSON

```json
{
  "email": "teacher@example.com",
  "password": "SecurePass123",
  "fullName": "John Doe",
  "phoneNumber": "9876543210",
  "whatsappNumber": "9876543210",
  "gender": "Male",
  "qualifications": "M.Sc. Mathematics",
  "certifications": "CTET Certified",
  "experience": 5,
  "hasVehicle": "Yes",
  "city": "Mumbai",
  "pin": "400001",
  "address": "123 Main Street",
  "aboutMe": "Experienced math teacher...",
  "mode": "Both",
  "expectedFeePerHour": 500,
  "preferredAreas": ["Andheri", "Bandra"],
  "subjectIds": [1, 2, 3],
  "additionalSubjects": [4, 5],
  "availabilities": [
    {
      "startTime": 540,
      "endTime": 720,
      "availableTimeForSlot": 180,
      "monday": true,
      "tuesday": true,
      "wednesday": true,
      "thursday": false,
      "friday": false,
      "saturday": false,
      "sunday": false
    }
  ],
  "educations": [
    {
      "degree": "M.Sc. Mathematics",
      "institution": "Mumbai University",
      "passingYear": 2015,
      "grade": "First Class"
    }
  ],
  "teacherAgreement": {
    "acceptedPrivacyPolicy": true,
    "acceptedTermsOfUse": true,
    "acceptedTeacherAgreement": true,
    "privacyPolicyDocumentId": 1,
    "termsOfUseDocumentId": 2,
    "teacherAgreementDocumentId": 3
  }
}
```

---

## Conclusion

The entity relationships are now properly structured with clear constraints:
- **TeacherAvailability:** 1-3 slots required
- **TeacherEducation:** 1-3 entries required
- **TeacherAgreement:** Exactly 1 required
- All relationships use CASCADE.ALL with orphanRemoval for proper lifecycle management
