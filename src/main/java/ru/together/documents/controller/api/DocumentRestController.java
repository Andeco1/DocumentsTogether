package ru.together.documents.controller.api;

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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/document")
public class DocumentRestController {
    private final DocumentService documentService;
    private final UserService userService;

    @GetMapping()
    public ResponseEntity<List<Document>> getPublicDocuments() {
        return ResponseEntity.ok(documentService.listPublicDocuments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Document> getPublicDocument(@PathVariable Long id) {
        return documentService.getPublicDocument(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
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

    @PutMapping("/{id}")
    public ResponseEntity<Document> updateDocumentMeta(@PathVariable Long id,
                                                       @RequestBody Map<String, String> body) {
        String author = body.get("author");
        String description = body.get("description");

        Document updated = documentService.updateDocumentMeta(id, author, description);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
        return ResponseEntity.noContent().build();
    }
}
