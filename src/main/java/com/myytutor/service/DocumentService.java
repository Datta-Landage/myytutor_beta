package com.myytutor.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.myytutor.dto.DocumentResponseDto;
import com.myytutor.entity.Document;
import com.myytutor.entity.Document.DocumentType;
import com.myytutor.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class DocumentService {

    private static final Logger log = LoggerFactory.getLogger(DocumentService.class);

    @Autowired
    private DocumentRepository repo;

    public DocumentResponseDto getLatest(DocumentType type) {
        log.info("Calling getLatest with type: {}", type);
        try {
            Document doc = repo.findTopByTypeOrderByPublishedAtDesc(type)
                    .orElseThrow(() -> {
                        log.error(
                                "No document found in database for type: {}. Please ensure the document is created first.",
                                type);
                        return new NoSuchElementException(
                                String.format(
                                        "No %s document found in the system. Administrator should create one first.",
                                        type.name()));
                    });
            log.info("Fetched latest document: {} with version: {}", type, doc.getVersion());
            return toDto(doc);
        } catch (Exception e) {
            log.error("Error fetching latest document of type {}: {}", type, e.getMessage());
            throw e;
        }
    }

    public DocumentResponseDto getByVersion(DocumentType type, String version) {
        log.info("Calling getByVersion with type: {}, version: {}", type, version);
        Document doc = repo.findByTypeAndVersion(type, version)
                .orElseThrow(() -> {
                    log.warn("No document found for type: {} with version: {}", type, version);
                    return new NoSuchElementException("No document: " + type + "@" + version);
                });
        log.info("Fetched document for version: {}", version);
        return toDto(doc);
    }

    public List<DocumentResponseDto> getLatestBulk(List<DocumentType> types) {
        log.info("Calling getLatestBulk for types: {}", types);
        List<DocumentResponseDto> result = repo.findByTypeInOrderByTypeAscPublishedAtDesc(types).stream()
                .collect(Collectors.toMap(Document::getType, d -> d, (a, b) -> a))
                .values().stream().map(this::toDto).collect(Collectors.toList());
        log.info("Fetched latest bulk documents: {}", result.size());
        return result;
    }

    private DocumentResponseDto toDto(Document d) {
        return new DocumentResponseDto(d.getType(), d.getVersion(), d.getContent());
    }
}
