package ru.together.documents.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.together.documents.dto.AuthRequest;
import ru.together.documents.dto.AuthResponse;
import ru.together.documents.service.JwtService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/docs")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/auth/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody AuthRequest authRequest) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()
                    )
            );

            String token = jwtService.generateToken(authRequest.getUsername());
            
            response.put("success", true);
            response.put("token", token);
            response.put("username", authRequest.getUsername());
            response.put("message", "Login successful");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Invalid username or password");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/auth/validate")
    public ResponseEntity<Map<String, Object>> validateToken(@RequestHeader("Authorization") String authHeader) {
        Map<String, Object> response = new HashMap<>();
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.put("valid", false);
            response.put("message", "No token provided");
            return ResponseEntity.badRequest().body(response);
        }
        
        String token = authHeader.substring(7);
        boolean isValid = jwtService.validateToken(token);
        
        if (isValid) {
            String username = jwtService.extractUsername(token);
            response.put("valid", true);
            response.put("username", username);
        } else {
            response.put("valid", false);
            response.put("message", "Invalid token");
        }
        
        return ResponseEntity.ok(response);
    }
}
