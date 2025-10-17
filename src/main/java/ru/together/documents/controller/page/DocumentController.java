package ru.together.documents.controller.page;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.together.documents.entity.LibUser;
import ru.together.documents.repository.UserRepository;
import ru.together.documents.service.DocumentService;
@Controller
@RequestMapping("/page")
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;
    private final UserRepository userRepository;

    @GetMapping("/list")
    public String getDocuments(String username, Model model){
        LibUser libUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        model.addAttribute("documents", documentService.getDocumentsByLibUser(libUser));
        return "documentsList";
    }

}
