package ru.together.documents.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.together.documents.entity.Document;
import ru.together.documents.entity.User;
import ru.together.documents.repository.DocumentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final DocumentRepository documentRepository;

    public List<Document> getDocumentsByUser(User user) {
        return documentRepository.findByUser(user);
    }

}
