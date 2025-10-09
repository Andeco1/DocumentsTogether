package ru.together.documents.dto;

import lombok.Data;

@Data
public class DocumentUploadRequest {
    private String title;

    private String author;

    private String description;
    
    private boolean isPublic = true;
}
