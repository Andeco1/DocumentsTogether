package ru.together.documents.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.together.documents.dto.UserPreferences;
import ru.together.documents.entity.LibUser;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class PersonalizationService {
    
    private final RedisTemplate<String, Object> redisTemplate;
    private final UserService userService;
    
    private static final String USER_PREFERENCES_PREFIX = "user_preferences:";
    private static final long PREFERENCES_TTL = 86400;
    
    public UserPreferences getUserPreferences(String username) {
        String key = USER_PREFERENCES_PREFIX + username;

        UserPreferences cached = (UserPreferences) redisTemplate.opsForValue().get(key);
        if (cached != null) {
            return cached;
        }

        LibUser user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        
        UserPreferences preferences = new UserPreferences(
                user.getUsername(),
                user.getTheme(),
                user.getLanguage()
        );

        redisTemplate.opsForValue().set(key, preferences, PREFERENCES_TTL, TimeUnit.SECONDS);
        
        return preferences;
    }
    
    public void updateUserPreferences(String username, String theme, String language) {
        LibUser user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        
        user.setTheme(theme);
        user.setLanguage(language);
        userService.save(user);

        UserPreferences preferences = new UserPreferences(username, theme, language);
        String key = USER_PREFERENCES_PREFIX + username;
        redisTemplate.opsForValue().set(key, preferences, PREFERENCES_TTL, TimeUnit.SECONDS);
    }

    public String getPersonalizedGreeting(String username) {
        UserPreferences prefs = getUserPreferences(username);
        
        String greeting;
        if (prefs.getLanguage().equals("en")) {
            greeting = "Welcome back, " + username + "!";
        } else {
            greeting = "Добро пожаловать, " + username + "!";
        }
        
        return greeting;
    }
    
    public String getPersonalizedThemeClass(String username) {
        UserPreferences prefs = getUserPreferences(username);
        return "theme-" + prefs.getTheme();
    }
}
