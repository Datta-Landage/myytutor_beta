package com.myytutor.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "teacher_agreements")
@EntityListeners(AuditingEntityListener.class)
public class TeacherAgreement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "privacy_policy_id", nullable = false)
	private Document privacyPolicy;

	@Column(name = "privacy_policy_accepted_at", nullable = false)
	private LocalDateTime privacyPolicyAcceptedAt;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "terms_of_use_id", nullable = false)
	private Document termsOfUse;

	@Column(name = "terms_of_use_accepted_at", nullable = false)
	private LocalDateTime termsOfUseAcceptedAt;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "teacher_agreement_id", nullable = false)
	private Document teacherAgreement;

	@Column(name = "teacher_agreement_accepted_at", nullable = false)
	private LocalDateTime teacherAgreementAcceptedAt;
	// Audit timestamps if desired
	@CreatedDate
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;
	@LastModifiedDate
	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;
	
	@PrePersist
	protected void onCreate() {
	    this.createdAt = LocalDateTime.now();
	    this.updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
	    this.updatedAt = LocalDateTime.now();
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Document getPrivacyPolicy() {
		return privacyPolicy;
	}

	public void setPrivacyPolicy(Document privacyPolicy) {
		this.privacyPolicy = privacyPolicy;
	}

	public Document getTermsOfUse() {
		return termsOfUse;
	}

	public void setTermsOfUse(Document termsOfUse) {
		this.termsOfUse = termsOfUse;
	}

	public Document getTeacherAgreement() {
		return teacherAgreement;
	}

	public void setTeacherAgreement(Document teacherAgreement) {
		this.teacherAgreement = teacherAgreement;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getPrivacyPolicyAcceptedAt() {
		return privacyPolicyAcceptedAt;
	}

	public void setPrivacyPolicyAcceptedAt(LocalDateTime privacyPolicyAcceptedAt) {
		this.privacyPolicyAcceptedAt = privacyPolicyAcceptedAt;
	}

	public LocalDateTime getTermsOfUseAcceptedAt() {
		return termsOfUseAcceptedAt;
	}

	public void setTermsOfUseAcceptedAt(LocalDateTime termsOfUseAcceptedAt) {
		this.termsOfUseAcceptedAt = termsOfUseAcceptedAt;
	}

	public LocalDateTime getTeacherAgreementAcceptedAt() {
		return teacherAgreementAcceptedAt;
	}

	public void setTeacherAgreementAcceptedAt(LocalDateTime teacherAgreementAcceptedAt) {
		this.teacherAgreementAcceptedAt = teacherAgreementAcceptedAt;
	}


}