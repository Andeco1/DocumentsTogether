package ru.together.documents.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.together.documents.entity.User;
import ru.together.documents.repository.UserRepository;
import ru.together.documents.service.DocumentService;
@Controller
@RequestMapping("/docs")
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;
    private final UserRepository userRepository;

    @GetMapping("/list")
    public String getDocuments(String username, Model model){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        model.addAttribute("documents", documentService.getDocumentsByUser(user));
        return "documentsList";
    }



}
