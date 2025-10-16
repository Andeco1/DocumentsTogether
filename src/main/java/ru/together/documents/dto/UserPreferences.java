package ru.together.documents.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPreferences {
    private String username;
    private String theme; // light, dark, colorblind
    private String language; // ru, en, etc.
}
