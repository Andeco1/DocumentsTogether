package ru.together.documents.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.together.documents.entity.Document;
import ru.together.documents.entity.User;

import java.util.List;


public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByUser(User user);
}
