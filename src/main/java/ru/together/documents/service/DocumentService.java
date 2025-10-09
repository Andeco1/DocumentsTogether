package ru.together.documents.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.together.documents.entity.Document;
import ru.together.documents.entity.User;
import ru.together.documents.repository.DocumentRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final DocumentRepository documentRepository;

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

}
