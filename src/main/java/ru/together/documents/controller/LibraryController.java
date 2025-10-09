package ru.together.documents.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.together.documents.service.DocumentService;

@Controller
@RequestMapping("/library")
@RequiredArgsConstructor
public class LibraryController {

    private final DocumentService documentService;

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
}


