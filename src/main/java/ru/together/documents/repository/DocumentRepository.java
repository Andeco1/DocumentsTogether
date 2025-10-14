package ru.together.documents.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.together.documents.entity.Document;
import ru.together.documents.entity.LibUser;

import java.util.List;


public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByLibUser(LibUser libUser);
    List<Document> findByIsPublicTrueOrderByCreatedAtDesc();
    List<Document> findByIsPublicTrueAndTitleContainingIgnoreCaseOrIsPublicTrueAndAuthorContainingIgnoreCase(String title, String author);
}
