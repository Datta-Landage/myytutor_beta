package com.myytutor.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.myytutor.dto.DocumentResponseDto;
import com.myytutor.entity.Document.DocumentType;
import com.myytutor.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {
    private static final Logger log = LoggerFactory.getLogger(DocumentController.class);
    @Autowired
    private DocumentService docService;

    @GetMapping("/{type}")
    public ResponseEntity<DocumentResponseDto> getLatest(@PathVariable String type) {
        log.info("Received request to get latest document of type: {}", type);
        DocumentType dt = DocumentType.valueOf(type.toUpperCase());
        log.info("Returning latest document for type: {}", type);
        return ResponseEntity.ok(docService.getLatest(dt));
    }

    @GetMapping("/{type}/{version}")
    public ResponseEntity<DocumentResponseDto> getByVersion(
            @PathVariable String type,
            @PathVariable String version) {
        log.info("Received request to get document of type: {} and version: {}", type, version);
        DocumentType dt = DocumentType.valueOf(type.toUpperCase());
        log.info("Returning document for type: {}, version: {}", type, version);
        return ResponseEntity.ok(docService.getByVersion(dt, version));
    }

    @GetMapping
    public ResponseEntity<List<DocumentResponseDto>> getLatestBulk(
            @RequestParam List<String> types) {
        log.info("Received request to get latest documents for types: {}", types);
        List<DocumentType> dtList = types.stream()
            .map(String::toUpperCase)
            .map(DocumentType::valueOf)
            .collect(Collectors.toList());
        log.info("Returning latest documents for types: {}", types);
        return	ResponseEntity.ok(docService.getLatestBulk(dtList));
    }
}
