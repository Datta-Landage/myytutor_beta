package com.myytutor.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Immutable;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Central, versioned document (privacy policy, terms, etc.).
 */
@Entity
@Table(name = "documents", uniqueConstraints = @UniqueConstraint(name = "uc_document_type_version", columnNames = {
		"type", "version" }), indexes = { @Index(name = "idx_document_type", columnList = "type"),
				@Index(name = "idx_document_type_publishedAt", columnList = "type, published_at DESC") })
@Immutable
@EntityListeners(AuditingEntityListener.class)
public class Document {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "type", length = 50, nullable = false)
	private DocumentType type;

	@Column(length = 20, updatable = false, nullable = false)
	private String version;

	@Column(name = "published_at", updatable = false, nullable = false)
	private LocalDateTime publishedAt;

	@Lob
	@Column(name = "content", columnDefinition = "TEXT", nullable = false, updatable = false)
	private String content;

	@CreatedDate
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;

	@Version
	private Long lockVersion;

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DocumentType getType() {
		return type;
	}

	public void setType(DocumentType type) {
		this.type = type;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public LocalDateTime getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(LocalDateTime publishedAt) {
		this.publishedAt = publishedAt;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public Long getLockVersion() {
		return lockVersion;
	}

	public void setLockVersion(Long lockVersion) {
		this.lockVersion = lockVersion;
	}

	public enum DocumentType {
		PRIVACY_POLICY, TERMS_OF_USE, TEACHER_AGREEMENT, USER_AGREEMENT, REFUND_POLICY, COOKIE_POLICY, FAQ_DOCUMENT
	}
}
