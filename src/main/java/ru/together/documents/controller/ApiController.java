package ru.together.documents.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.together.documents.dto.DocumentUploadRequest;
import ru.together.documents.entity.Document;
import ru.together.documents.entity.LibUser;
import ru.together.documents.service.DocumentService;
import ru.together.documents.service.UserService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {
    private final DocumentService documentService;
    private final UserService userService;

    /**
     * Получить список всех публичных документов
     */
    @GetMapping("/document")
    public ResponseEntity<List<Document>> getPublicDocuments() {
        return ResponseEntity.ok(documentService.listPublicDocuments());
    }
    /*
     * Получить один публичный документ по ID
     */
    @GetMapping("/document/{id}")
    public ResponseEntity<Document> getPublicDocument(@PathVariable Long id) {
        return documentService.getPublicDocument(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Загрузить новый документ для пользователя
     */
    @PostMapping("/document")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> uploadDocument(
            @RequestParam("title") String title,
            @RequestParam("author") String author,
            @RequestParam("description") String description,
            @RequestParam("isPublic") boolean isPublic,
            @RequestBody MultipartFile file) {

        Map<String, Object> response = new HashMap<>();

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                response.put("success", false);
                response.put("message", "Authentication required");
                return ResponseEntity.status(401).body(response);
            }

            String username = authentication.getName();
            LibUser libUser = userService.getUserByUsername(username)
                    .orElseThrow(() -> new RuntimeException("LibUser not found"));

            DocumentUploadRequest request = new DocumentUploadRequest();
            request.setTitle(title);
            request.setAuthor(author);
            request.setDescription(description);
            request.setPublic(isPublic);

            Document document = documentService.createDocument(request, file, libUser);

            response.put("success", true);
            response.put("message", "Document uploaded successfully");
            response.put("documentId", document.getDocumentId());

            return ResponseEntity.ok(response);

        } catch (IOException e) {
            response.put("success", false);
            response.put("message", "Failed to upload file: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    /**
     * Обновить автора и описание документа
     */
    @PutMapping("/document/{id}")
    public ResponseEntity<Document> updateDocumentMeta(@PathVariable Long id,
                                                       @RequestBody Map<String, String> body) {
        String author = body.get("author");
        String description = body.get("description");

        Document updated = documentService.updateDocumentMeta(id, author, description);
        return ResponseEntity.ok(updated);
    }

    /**
     * Удалить документ по ID
     */
    @DeleteMapping("/document/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/user")
    public ResponseEntity<?> createUser(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String confirmPassword) {
        try {
            LibUser created = userService.register(username, email, password, confirmPassword);
            return ResponseEntity.ok(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    // READ ONE BY ID
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<LibUser> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // UPDATE
    @PutMapping("/user/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String password) {
        try {
            LibUser updated = userService.updateUser(id, email, password);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // DELETE
    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
