package ru.together.documents.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.together.documents.dto.DocumentUploadRequest;
import ru.together.documents.entity.Document;
import ru.together.documents.entity.User;
import ru.together.documents.repository.UserRepository;
import ru.together.documents.service.DocumentService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/library")
@RequiredArgsConstructor
public class LibraryController {

    private final DocumentService documentService;
    private final UserRepository userRepository;

    @GetMapping({"", "/"})
    public String list(@RequestParam(value = "q", required = false) String q, Model model) {
        if (q == null || q.isBlank()){
            model.addAttribute("documents", documentService.listPublicDocuments());
        } else {
            model.addAttribute("documents", documentService.searchPublicDocuments(q));
        }
        model.addAttribute("q", q);
        return "library";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        return documentService.getPublicDocument(id)
                .map(doc -> {
                    model.addAttribute("doc", doc);
                    return "libraryDetails";
                })
                .orElse("redirect:/library");
    }

    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> uploadDocument(
            @RequestParam("title") String title,
            @RequestParam("author") String author,
            @RequestParam("description") String description,
            @RequestParam("isPublic") boolean isPublic,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "username", required = false) String username) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            // For now, create a default user or get from session
            // TODO: Implement proper authentication/session management
            User user;
            if (username != null && !username.isEmpty()) {
                user = userRepository.findByUsername(username)
                        .orElseThrow(() -> new RuntimeException("User not found"));
            } else {
                // Create a default user for testing
                user = new User();
                user.setUserId(1L);
                user.setUsername("default");
                user.setEmail("default@example.com");
                user.setPassword("default");
            }

            DocumentUploadRequest request = new DocumentUploadRequest();
            request.setTitle(title);
            request.setAuthor(author);
            request.setDescription(description);
            request.setPublic(isPublic);

            Document document = documentService.createDocument(request, file, user);
            
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
}


