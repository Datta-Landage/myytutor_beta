package com.myytutor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.myytutor.entity.Document;
import com.myytutor.entity.Document.DocumentType;
import java.util.List;
import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    Optional<Document> findTopByTypeOrderByPublishedAtDesc(DocumentType type);
    Optional<Document> findByTypeAndVersion(DocumentType type, String version);
    List<Document> findByTypeInOrderByTypeAscPublishedAtDesc(List<DocumentType> types);
}

