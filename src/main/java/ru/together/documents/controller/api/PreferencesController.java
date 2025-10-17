package ru.together.documents.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.together.documents.dto.UserPreferences;
import ru.together.documents.service.PersonalizationService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/preferences")
@RequiredArgsConstructor
public class PreferencesController {

    private final PersonalizationService personalizationService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getUserPreferences() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body(Map.of("error", "Not authenticated"));
        }

        String username = authentication.getName();
        UserPreferences preferences = personalizationService.getUserPreferences(username);
        
        Map<String, Object> response = new HashMap<>();
        response.put("username", preferences.getUsername());
        response.put("theme", preferences.getTheme());
        response.put("language", preferences.getLanguage());
        response.put("greeting", personalizationService.getPersonalizedGreeting(username));
        
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> updatePreferences(
            @RequestParam String theme,
            @RequestParam String language) {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body(Map.of("error", "Not authenticated"));
        }

        String username = authentication.getName();

        if (!theme.matches("^(light|dark|colorblind)$")) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid theme"));
        }

        if (!language.matches("^(ru|en)$")) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid language"));
        }

        personalizationService.updateUserPreferences(username, theme, language);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Preferences updated successfully");
        response.put("greeting", personalizationService.getPersonalizedGreeting(username));
        
        return ResponseEntity.ok(response);
    }
}
