package ru.together.documents.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.together.documents.dto.DocumentUploadRequest;
import ru.together.documents.entity.Document;
import ru.together.documents.entity.User;
import ru.together.documents.repository.DocumentRepository;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final S3Service s3Service;

    public List<Document> getDocumentsByUser(User user) {
        return documentRepository.findByUser(user);
    }

    public List<Document> listPublicDocuments() {
        return documentRepository.findByIsPublicTrueOrderByCreatedAtDesc();
    }

    public List<Document> searchPublicDocuments(String query) {
        if (query == null || query.isBlank()) {
            return listPublicDocuments();
        }
        return documentRepository
                .findByIsPublicTrueAndTitleContainingIgnoreCaseOrIsPublicTrueAndAuthorContainingIgnoreCase(query, query);
    }

    public Optional<Document> getPublicDocument(Long id) {
        return documentRepository.findById(id)
                .filter(Document::isPublic);
    }

    public Document createDocument(DocumentUploadRequest request, MultipartFile file, User user) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be empty");
        }

        // Generate unique file key for S3
        String fileKey = s3Service.generateFileKey(file.getOriginalFilename());
        
        // Upload file to S3
        String fileUrl = s3Service.uploadFile(file, fileKey);

        // Create document entity
        Document document = new Document();
        document.setUser(user);
        document.setFile_name(file.getOriginalFilename());
        document.setFile_url(fileUrl);
        document.setTitle(request.getTitle());
        document.setAuthor(request.getAuthor());
        document.setDescription(request.getDescription());
        document.setPublic(request.isPublic());
        document.setCreatedAt(Instant.now());

        // Save to database
        return documentRepository.save(document);
    }

}
