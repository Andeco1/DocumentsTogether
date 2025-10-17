package ru.together.documents.controller.page;

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
import ru.together.documents.service.PersonalizationService;
import ru.together.documents.service.JwtService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/page")
@RequiredArgsConstructor
public class LibraryController {

    private final DocumentService documentService;
    private final UserRepository userRepository;
    private final PersonalizationService personalizationService;
    private final JwtService jwtService;

    @GetMapping({"", "/"})
    public String list(
            @RequestParam(value = "q", required = false) String q, Model model,
            @CookieValue(name = "access_token", required = false) String token
    ) {
        if (q == null || q.isBlank()){
            model.addAttribute("documents", documentService.listPublicDocuments());
        } else {
            model.addAttribute("documents", documentService.searchPublicDocuments(q));
        }
        model.addAttribute("q", q);

        if (token != null && !token.isEmpty()) {
            try {
                String username = jwtService.extractUsername(token);
                model.addAttribute("username", username);
                model.addAttribute("userGreeting", personalizationService.getPersonalizedGreeting(username));
                model.addAttribute("themeClass", personalizationService.getPersonalizedThemeClass(username));
            } catch (Exception e) {
                model.addAttribute("userGreeting", "Добро пожаловать в библиотеку!");
                model.addAttribute("themeClass", "theme-light");
            }
        } else {
            model.addAttribute("userGreeting", "Добро пожаловать в библиотеку!");
            model.addAttribute("themeClass", "theme-light");
        }
        
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


