package com.myytutor.dto;

import com.myytutor.entity.Document.DocumentType;

public class DocumentResponseDto {
    private DocumentType type;
    private String version;
    private String content;

    public DocumentResponseDto(DocumentType type, String version, String content) {
        this.type = type;
        this.version = version;
        this.content = content;
    }

    public DocumentType getType() { return type; }
    public void setType(DocumentType type) { this.type = type; }

    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
