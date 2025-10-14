package ru.together.documents.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.together.documents.dto.DocumentUploadRequest;
import ru.together.documents.entity.Document;
import ru.together.documents.entity.LibUser;
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
            @RequestParam("file") MultipartFile file) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Get authenticated libUser
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                response.put("success", false);
                response.put("message", "Authentication required");
                return ResponseEntity.status(401).body(response);
            }
            
            String username = authentication.getName();
            LibUser libUser = userRepository.findByUsername(username)
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
}


